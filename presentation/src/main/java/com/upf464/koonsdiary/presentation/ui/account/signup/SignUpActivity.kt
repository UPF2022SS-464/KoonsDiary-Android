package com.upf464.koonsdiary.presentation.ui.account.signup

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.upf464.koonsdiary.presentation.databinding.ActivityEmailSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var binding: ActivityEmailSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBinding()
        setListeners()
    }

    private fun setBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.recyclerViewSignUpEmailImage.adapter = UserImageListAdapter { index ->
            viewModel.selectImageAt(index)
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    SignUpViewModel.SignUpEvent.Success ->
                        Toast.makeText(
                            this@SignUpActivity,
                            "회원가입 완료",
                            Toast.LENGTH_LONG
                        ).show()
                    SignUpViewModel.SignUpEvent.NoImageSelected ->
                        Toast.makeText(
                            this@SignUpActivity,
                            "이미지 선택 안함",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }

        lifecycleScope.launch {
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            binding.editTextSignUpEmailField1.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) inputMethod.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }

            viewModel.pageFlow.collect { page ->
                when (page) {
                    SignUpViewModel.SignUpPage.IMAGE -> {
                        inputMethod.hideSoftInputFromWindow(
                            binding.editTextSignUpEmailField1.windowToken,
                            InputMethodManager.HIDE_IMPLICIT_ONLY
                        )
                        inputMethod.hideSoftInputFromWindow(
                            binding.editTextSignUpEmailField2.windowToken,
                            InputMethodManager.HIDE_IMPLICIT_ONLY
                        )
                    }
                    else -> binding.editTextSignUpEmailField1.requestFocus()
                }
            }
        }
    }
}

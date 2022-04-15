package com.upf464.koonsdiary.presentation.ui.account.signup

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.upf464.koonsdiary.presentation.databinding.ActivityKakaoSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KakaoSignUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<KakaoSignUpViewModel>()

    private lateinit var binding: ActivityKakaoSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBinding()
        setListeners()
    }

    private fun setBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.recyclerViewSignUpKakaoImage.adapter = UserImageListAdapter { index ->
            viewModel.selectImageAt(index)
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    KakaoSignUpViewModel.SignUpEvent.Success ->
                        Toast.makeText(
                            this@KakaoSignUpActivity,
                            "회원가입 완료",
                            Toast.LENGTH_LONG
                        ).show()
                    KakaoSignUpViewModel.SignUpEvent.NoImageSelected ->
                        Toast.makeText(
                            this@KakaoSignUpActivity,
                            "이미지 선택 안함",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }

        lifecycleScope.launch {
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            binding.editTextSignUpKakaoField.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) inputMethod.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }

            viewModel.pageFlow.collect { page ->
                when (page) {
                    KakaoSignUpViewModel.KakaoSignUpPage.IMAGE -> {
                        inputMethod.hideSoftInputFromWindow(
                            binding.editTextSignUpKakaoField.windowToken,
                            InputMethodManager.HIDE_IMPLICIT_ONLY
                        )
                    }
                    else -> binding.editTextSignUpKakaoField.requestFocus()
                }
            }
        }
    }
}

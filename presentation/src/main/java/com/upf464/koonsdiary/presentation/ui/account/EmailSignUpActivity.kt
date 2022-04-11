package com.upf464.koonsdiary.presentation.ui.account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.upf464.koonsdiary.presentation.databinding.ActivityEmailSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailSignUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<EmailSignUpViewModel>()

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
    }

    private fun setListeners() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { }
        }
    }
}

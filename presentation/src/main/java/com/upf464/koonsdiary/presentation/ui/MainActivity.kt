package com.upf464.koonsdiary.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.account.signup.KakaoSignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, KakaoSignUpActivity::class.java))
    }
}

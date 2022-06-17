package com.upf464.koonsdiary.presentation.ui.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.upf464.koonsdiary.domain.model.Sentiment

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

object KoonsColor {
    val Green = Color(0xFFB5D3B6)
    val Red = Color(0xFFEB7487)

    val Black100 = Color(0xFF15141F)
    val Black60 = Color(0xFFA2A0A8)
    val Black40 = Color(0xFFCCCACF)
    val Black20 = Color(0xFFDCDBE0)
    val Black10 = Color(0xFFE8E8E8)
    val Black5 = Color(0xFFF9F9FA)

    val KakaoBackground = Color(0xFFF7E600)
    val KakaoContent = Color(0xFF3A1D1D)
}

@Composable
fun transparentTextColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent
)

fun colorOf(sentiment: Sentiment) = when (sentiment) {
    Sentiment.VERY_SAD -> Color(0xFFEB7487)
    Sentiment.SAD -> Color(0xFFF7C7C7)
    Sentiment.NORMAL -> Color(0xFFB5D3B6)
    Sentiment.GOOD -> Color(0xFF88B0DC)
    Sentiment.VERY_GOOD -> Color(0xFF3F7DC6)
}

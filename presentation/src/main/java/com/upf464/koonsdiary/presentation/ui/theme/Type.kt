package com.upf464.koonsdiary.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

object KoonsTypography {
    val H1 = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    val H2 = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
    val H3 = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
    val H4 = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
    val H5 = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
    val H6 = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
    val H7 = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
    val H8 = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)

    val BodyRegular = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
    val BodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
    val BodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
    val BodyMoreSmall = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Normal)
}

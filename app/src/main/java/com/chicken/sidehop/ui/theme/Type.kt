package com.chicken.sidehop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.R

private val passionOne = FontFamily(
    Font(resId = R.font.passion_one_regular, weight = FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = passionOne,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = passionOne,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp
    ),
    labelLarge = TextStyle(
        fontFamily = passionOne,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp
    )
)
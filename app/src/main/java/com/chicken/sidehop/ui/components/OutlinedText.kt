package com.chicken.sidehop.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.R

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    outline: Color,
    fontSize: TextUnit = 32.sp,
    outlineWidth: Dp = 2.dp
) {
    val fontFamily = FontFamily(
        Font(R.font.passion_one_regular, weight = FontWeight.Normal)
    )

    val style = TextStyle(
        fontFamily = fontFamily,
        fontSize = fontSize
    )

    Box(modifier = modifier) {
        val offset = outlineWidth

        Text(
            text = text,
            style = style,
            color = outline,
            modifier = Modifier.offset(-offset, -offset)
        )
        Text(
            text = text,
            style = style,
            color = outline,
            modifier = Modifier.offset(-offset, offset)
        )
        Text(
            text = text,
            style = style,
            color = outline,
            modifier = Modifier.offset(offset, -offset)
        )
        Text(
            text = text,
            style = style,
            color = outline,
            modifier = Modifier.offset(offset, offset)
        )

        Text(
            text = text,
            style = style,
            color = color
        )
    }
}

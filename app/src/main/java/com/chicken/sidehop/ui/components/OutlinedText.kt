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

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    outline: Color,
    style: TextStyle,
    outlineWidth: Dp = 2.dp
) {
    Box(modifier = modifier) {
        val offset = outlineWidth
        Text(text = text, style = style, color = outline, modifier = Modifier.offset(x = -offset, y = -offset))
        Text(text = text, style = style, color = outline, modifier = Modifier.offset(x = -offset, y = offset))
        Text(text = text, style = style, color = outline, modifier = Modifier.offset(x = offset, y = -offset))
        Text(text = text, style = style, color = outline, modifier = Modifier.offset(x = offset, y = offset))
        Text(text = text, style = style, color = color)
    }
}

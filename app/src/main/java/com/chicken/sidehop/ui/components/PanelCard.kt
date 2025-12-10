package com.chicken.sidehop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.ui.theme.CreamPanel
import com.chicken.sidehop.ui.theme.OutlineDark

@Composable
fun PanelCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 18.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(CreamPanel, shape = RoundedCornerShape(cornerRadius))
            .border(4.dp, OutlineDark, shape = RoundedCornerShape(cornerRadius))
            .padding(16.dp)
    ) {
        content()
    }
}

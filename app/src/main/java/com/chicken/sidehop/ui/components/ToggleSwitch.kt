package com.chicken.sidehop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.ui.theme.CreamPanel
import com.chicken.sidehop.ui.theme.OutlineDark
import com.chicken.sidehop.ui.theme.YellowDeep

@Composable
fun ToggleSwitch(
    label: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedText(
            text = label,
            color = Color.White,
            outline = OutlineDark,
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .padding(start = 12.dp)
                .width(72.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (enabled) YellowDeep else OutlineDark)
                .clickable { onToggle(!enabled) }
        ) {
            Box(
                modifier = Modifier
                    .align(if (enabled) Alignment.CenterEnd else Alignment.CenterStart)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(CreamPanel)
                    .width(28.dp)
                    .height(24.dp)
            )
        }
    }
}

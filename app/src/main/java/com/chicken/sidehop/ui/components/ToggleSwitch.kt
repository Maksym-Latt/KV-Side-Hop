package com.chicken.sidehop.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.theme.CreamPanel
import com.chicken.sidehop.ui.theme.OutlineDark
import com.chicken.sidehop.ui.theme.YellowDeep

@Composable
fun ToggleSwitch(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit
) {
    val interaction = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .width(120.dp)
            .height(56.dp)
            .clickable(
                interactionSource = interaction,
                indication = null
            ) { onToggle(!enabled) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.toggle_bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        val knob = if (enabled) R.drawable.toggle_knob_green else R.drawable.toggle_knob_yellow

        Image(
            painter = painterResource(id = knob),
            contentDescription = null,
            modifier = Modifier
                .align(if (enabled) Alignment.CenterEnd else Alignment.CenterStart)
                .padding(horizontal = 2.dp)
                .size(52.dp),
            contentScale = ContentScale.Fit
        )
    }
}
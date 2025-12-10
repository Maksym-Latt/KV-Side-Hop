package com.chicken.sidehop.ui.components

import android.R.attr.textColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.theme.OutlineDark
import com.chicken.sidehop.ui.theme.RedPrimary
import com.chicken.sidehop.ui.theme.YellowDeep

@Composable
fun RoundButton(
    icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 84.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size / 2))
            .clickable(onClick = onClick)
    ) {
        val frameSize = size * 1.12f
        val innerSize = size * 0.92f

        Image(
            painter = painterResource(id = R.drawable.wood_round),
            contentDescription = null,
            modifier = Modifier
                .size(frameSize)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.btn_round),
            contentDescription = null,
            modifier = Modifier
                .size(innerSize)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size * 0.45f)
        )
    }
}

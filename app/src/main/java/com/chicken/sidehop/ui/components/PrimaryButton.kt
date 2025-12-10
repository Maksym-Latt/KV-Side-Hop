package com.chicken.sidehop.ui.components

import android.R.attr.scaleX
import android.R.attr.scaleY
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.theme.OutlineDark
import com.chicken.sidehop.ui.theme.RedPrimary
import com.chicken.sidehop.ui.theme.YellowDeep

enum class ButtonStyle { Yellow, Red }

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.Yellow,
    height: Dp = 64.dp,
    woodScale: Float = 2.02f,
    innerScale: Float = 2f,
    onClick: () -> Unit,
) {
    val background = when (style) {
        ButtonStyle.Yellow -> R.drawable.btn_yellow
        ButtonStyle.Red -> R.drawable.btn_red
    }
    val textColor = when (style) {
        ButtonStyle.Yellow -> Color.White
        ButtonStyle.Red -> Color.White
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.wood_rectangle),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .graphicsLayer {
                    scaleX = woodScale
                    scaleY = woodScale
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = background),
            contentDescription = text,
            modifier = Modifier
                .fillMaxHeight()
                .graphicsLayer {
                    scaleX = innerScale
                    scaleY = innerScale
                },
            contentScale = ContentScale.Fit
        )

        OutlinedText(
            text = text,
            color = textColor,
            outline = OutlineDark,
            outlineWidth = 2.dp,
            modifier = Modifier
        )
    }
}
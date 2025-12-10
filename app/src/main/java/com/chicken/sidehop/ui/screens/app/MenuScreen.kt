package com.chicken.sidehop.ui.screens.app

import android.R.attr.scaleX
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.components.RoundButton
import com.chicken.sidehop.ui.theme.OutlineDark
import androidx.compose.runtime.getValue

@Composable
fun MenuScreen(
    onPlay: () -> Unit,
    onSettings: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.onMenuVisible() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        val infinite = rememberInfiniteTransition()

        val leftChickenOffset by infinite.animateFloat(
            initialValue = 0f,
            targetValue = -20f,
            animationSpec = infiniteRepeatable(
                animation = tween(1600, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        val rightChickenOffset by infinite.animateFloat(
            initialValue = -10f,
            targetValue = 15f,
            animationSpec = infiniteRepeatable(
                animation = tween(1800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(
            painter = painterResource(id = R.drawable.chicken),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-90).dp, y = (80 + leftChickenOffset).dp)
                .size(220.dp)
                .graphicsLayer { scaleX = -1f },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.chicken),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 90.dp, y = rightChickenOffset.dp)
                .size(220.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                RoundButton(
                    icon = R.drawable.ic_settings,
                    size = 64.dp,
                    onClick = {
                        viewModel.onButtonTap()
                        onSettings()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(300.dp),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.weight(5f))

            RoundButton(
                icon = R.drawable.ic_play,
                size = 140.dp,
                onClick = {
                    viewModel.onButtonTap()
                    onPlay()
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

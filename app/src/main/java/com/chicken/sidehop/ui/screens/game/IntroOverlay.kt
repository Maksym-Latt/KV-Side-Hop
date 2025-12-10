package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.theme.OutlineDark

@Composable
fun IntroOverlay(onStart: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .pointerInput(Unit) {
                detectTapGestures { onStart() }
            }
    ) {
        PanelCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = "HOW TO PLAY",
                    color = Color(0xffffffff),
                    outline = OutlineDark,
                    fontSize = 50.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedText(text = "Left side", color = Color.White, outline = OutlineDark)
                        OutlinedText(text = "Jump", color = Color.White, outline = OutlineDark)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedText(text = "Right side", color = Color.White, outline = OutlineDark)
                        OutlinedText(text = "Dash sideways", color = Color.White, outline = OutlineDark)
                    }
                }
                OutlinedText(
                    text = "The chicken runs between lanes and can catch items even in the middle.",
                    color = Color.White,
                    outline = OutlineDark,
                )
                OutlinedText(
                    text = "Tap anywhere to start!",
                    color = Color(0xff93ec7b),
                    outline = OutlineDark,
                )
            }
        }
    }
}

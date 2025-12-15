package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.theme.OutlineDark
@Composable
fun IntroOverlay(
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .pointerInput(Unit) { detectTapGestures { onStart() } }
    ) {
        PanelCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
                .widthIn(max = 720.dp)
                .heightIn(max = 420.dp) // ключ: не раздуваем по высоте
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 14.dp) // чтобы контент не прилипал
                    .verticalScroll(rememberScrollState()),        // если не влезло
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = "HOW TO PLAY",
                    color = Color.White,
                    outline = OutlineDark,
                    fontSize = 44.sp,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedText(
                            text = "Jump",
                            color = Color.White,
                            outline = OutlineDark,
                            fontSize = 26.sp,
                        )
                        OutlinedText(
                            text = "Tap the Jump button to hop upward.",
                            color = Color.White,
                            outline = OutlineDark,
                            fontSize = 20.sp,
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedText(
                            text = "Slide",
                            color = Color.White,
                            outline = OutlineDark,
                            fontSize = 26.sp,
                        )
                        OutlinedText(
                            text = "Use the Slide button to switch lanes fast.",
                            color = Color.White,
                            outline = OutlineDark,
                            fontSize = 20.sp,
                        )
                    }
                }

                OutlinedText(
                    text = "The chicken runs between lanes and can catch items even in the middle.",
                    color = Color.White,
                    outline = OutlineDark,
                    fontSize = 20.sp,
                )

                OutlinedText(
                    text = "Tap anywhere to start!",
                    color = Color(0xff93ec7b),
                    outline = OutlineDark,
                    fontSize = 26.sp,
                )
            }
        }
    }
}

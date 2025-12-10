package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.theme.OutlineDark

@Composable
fun PauseOverlay(
    onResume: () -> Unit,
    onMenu: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    ) {
        PanelCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedText(
                    text = "PAUSE",
                    color = MaterialTheme.colorScheme.secondary,
                    outline = OutlineDark,
                )
                PrimaryButton(text = "RESUME", onClick = onResume, style = ButtonStyle.Yellow)
                PrimaryButton(text = "MENU", onClick = onMenu, style = ButtonStyle.Red)
            }
        }
    }
}
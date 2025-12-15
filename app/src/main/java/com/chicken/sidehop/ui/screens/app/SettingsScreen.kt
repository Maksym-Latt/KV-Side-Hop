package com.chicken.sidehop.ui.screens.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.components.ActionButton
import com.chicken.sidehop.ui.components.ToggleSwitch

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val soundEnabled = viewModel.isSoundEnabled.collectAsState()
    val musicEnabled = viewModel.isMusicEnabled.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5F7FF))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        PanelCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                OutlinedText(
                    text = "SETTINGS",
                    color = Color(0xffffffff),
                    outline = Color(0xFF5E3B1E),
                    outlineWidth = 3.dp,
                    fontSize = 50.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedText(
                        text = "SOUND",
                        color = Color.White,
                        outline = Color(0xFF5E3B1E),
                        outlineWidth = 1.dp,
                        fontSize = 40.sp
                    )
                    ToggleSwitch(
                        enabled = soundEnabled.value,
                        onToggle = viewModel::onSoundToggle
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedText(
                        text = "MUSIC",
                        color = Color.White,
                        outline = Color(0xFF5E3B1E),
                        outlineWidth = 1.dp,
                        fontSize = 40.sp
                    )
                    ToggleSwitch(
                        enabled = musicEnabled.value,
                        onToggle = viewModel::onMusicToggle
                    )
                }

                ActionButton(
                    text = "MENU",
                    onClick = onBack,
                    style = ButtonStyle.Red,
                    woodScale = 2.02f,
                    innerScale = 2f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

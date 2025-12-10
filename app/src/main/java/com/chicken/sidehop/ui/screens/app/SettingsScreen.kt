package com.chicken.sidehop.ui.screens.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.components.ToggleSwitch
import com.chicken.sidehop.ui.theme.OutlineDark

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
            .background(MaterialTheme.colorScheme.background)
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = "SETTINGS",
                    color = MaterialTheme.colorScheme.secondary,
                    outline = OutlineDark,
                    style = MaterialTheme.typography.titleLarge
                )
                ToggleSwitch(
                    label = "SOUND",
                    enabled = soundEnabled.value,
                    onToggle = viewModel::onSoundToggle
                )
                ToggleSwitch(
                    label = "MUSIC",
                    enabled = musicEnabled.value,
                    onToggle = viewModel::onMusicToggle
                )
                Spacer(modifier = Modifier.height(4.dp))
                PrimaryButton(
                    text = "MENU",
                    onClick = onBack,
                    style = ButtonStyle.Red
                )
            }
        }
    }
}

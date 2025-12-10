package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.hilt.navigation.compose.hiltViewModel
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.components.ScoreBadge
import com.chicken.sidehop.ui.components.ToggleSwitch
import com.chicken.sidehop.ui.theme.OutlineDark
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onExit: () -> Unit,
    onGameOver: (score: Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showSettings by remember { mutableStateOf(false) }

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            delay(400)
            onGameOver(state.score)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) viewModel.pauseGame()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_game),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        BoxWithConstraints(modifier = Modifier.matchParentSize()) {
            val laneOffset = maxWidth * 0.22f
            val groundHeight = maxHeight * 0.78f

            state.items.forEach { item ->
                val x = if (item.lane == Lane.LEFT) laneOffset else maxWidth - laneOffset
                val y = groundHeight * item.yProgress
                Image(
                    painter = painterResource(id = item.type.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .offset(x = x - 32.dp, y = y - 32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            val chickenX = if (state.chickenLane == Lane.LEFT) laneOffset else maxWidth - laneOffset
            val chickenY = groundHeight + (state.chickenJumpOffset * 120.dp)
            Image(
                painter = painterResource(id = R.drawable.chicken),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .offset(x = chickenX - 70.dp, y = chickenY - 80.dp),
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PauseButton(onClick = { viewModel.togglePause() })
                Spacer(modifier = Modifier.weight(1f))
                ScoreBadge(score = state.score)
            }

            ControlHints(modifier = Modifier.align(Alignment.BottomCenter))

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val center = size.width / 2
                            if (offset.x < center) {
                                viewModel.onJump()
                            } else {
                                viewModel.onSwitchLane()
                            }
                        }
                    }
            )
        }

        if (state.isPaused) {
            PauseOverlay(
                onResume = { viewModel.resumeGame() },
                onMenu = onExit,
                onSettings = { showSettings = true }
            )
        }

        if (showSettings) {
            SettingsOverlay(
                onClose = { showSettings = false },
                onMenu = onExit
            )
        }

        if (state.showWrongPick) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                OutlinedText(
                    text = "Wrong pick!",
                    color = Color.White,
                    outline = OutlineDark,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 140.dp)
                )
            }
        }
    }
}

@Composable
private fun PauseButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.85f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .width(8.dp)
                    .clip(CircleShape)
                    .background(OutlineDark)
            )
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .width(8.dp)
                    .clip(CircleShape)
                    .background(OutlineDark)
            )
        }
    }
}

@Composable
private fun ControlHints(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(bottom = 24.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(start = 18.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedText(
                text = "JUMP",
                color = Color.White,
                outline = OutlineDark,
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedText(
                text = "⬆",
                color = Color.White,
                outline = OutlineDark,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Column(
            modifier = Modifier.padding(end = 18.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedText(
                text = "SWAP",
                color = Color.White,
                outline = OutlineDark,
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedText(
                text = "⇄",
                color = Color.White,
                outline = OutlineDark,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun PauseOverlay(
    onResume: () -> Unit,
    onMenu: () -> Unit,
    onSettings: () -> Unit
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
                    style = MaterialTheme.typography.titleLarge
                )
                PrimaryButton(text = "RESUME", onClick = onResume, style = ButtonStyle.Yellow)
                PrimaryButton(text = "SETTINGS", onClick = onSettings, style = ButtonStyle.Yellow)
                PrimaryButton(text = "MENU", onClick = onMenu, style = ButtonStyle.Red)
            }
        }
    }
}

@Composable
private fun SettingsOverlay(
    onClose: () -> Unit,
    onMenu: () -> Unit
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val soundEnabled = viewModel.isSoundEnabled.collectAsState()
    val musicEnabled = viewModel.isMusicEnabled.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
    ) {
        PanelCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = "SETTINGS",
                    color = MaterialTheme.colorScheme.secondary,
                    outline = OutlineDark,
                    style = MaterialTheme.typography.titleLarge
                )
                ToggleSwitch(label = "SOUND", enabled = soundEnabled.value, onToggle = viewModel::onSoundToggle)
                ToggleSwitch(label = "MUSIC", enabled = musicEnabled.value, onToggle = viewModel::onMusicToggle)
                PrimaryButton(text = "CLOSE", onClick = onClose, style = ButtonStyle.Yellow)
                PrimaryButton(text = "MENU", onClick = onMenu, style = ButtonStyle.Red)
            }
        }
    }
}

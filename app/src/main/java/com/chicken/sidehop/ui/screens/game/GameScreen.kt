package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.RoundButton
import com.chicken.sidehop.ui.components.ScoreBadge
import com.chicken.sidehop.ui.theme.OutlineDark
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onExit: () -> Unit,
    onGameOver: (score: Int) -> Unit
) {
    val debugging = true
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            delay(2000)
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
            val itemFallHeight = maxHeight * 0.78f
            val chickenSize = 140.dp

            state.items.forEach { item ->
                val x = maxWidth * item.xPosition
                val y = itemFallHeight * item.yProgress
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .offset(x = x - 32.dp, y = y - 32.dp)
                ) {
                    if (debugging) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .border(2.dp, Color.Red, CircleShape)
                        )
                    }
                    Image(
                        painter = painterResource(id = item.type.icon),
                        contentDescription = null,
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            val chickenX = maxWidth * state.chickenX
            val density = LocalDensity.current

            val chickenY: Dp = with(density) {
                val baseY = ((maxHeight / 2) + chickenSize / 2).toPx()
                val jumpOffsetPx = state.chickenJumpOffset * 120.dp.toPx()
                (baseY + jumpOffsetPx - chickenSize.toPx()).toDp()
            }

            Image(
                painter = painterResource(id = R.drawable.chicken_play),
                contentDescription = null,
                modifier = Modifier
                    .size(chickenSize)
                    .offset(x = chickenX - 70.dp, y = chickenY),
                contentScale = ContentScale.Fit
            )

            if (debugging) {
                val verticalContact = (CollisionConfig.CONTACT_THRESHOLD +
                    state.chickenJumpOffset * CollisionConfig.JUMP_INFLUENCE)
                    .coerceIn(0f, 1f)
                val catchAreaWidth = maxWidth * (CollisionConfig.CATCH_WIDTH * 2f)
                val catchAreaHeight = itemFallHeight * CollisionConfig.GROUND_RANGE
                val catchY = itemFallHeight * verticalContact
                Box(
                    modifier = Modifier
                        .size(width = catchAreaWidth, height = catchAreaHeight)
                        .offset(x = chickenX - (catchAreaWidth / 2), y = catchY)
                        .border(2.dp, Color.Green)
                )
            }

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

            Row(
                modifier = Modifier
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PauseButton(
                    onClick = { viewModel.togglePause() },
                    enabled = !state.isGameOver
                )
                Spacer(modifier = Modifier.weight(1f))
                ScoreBadge(score = state.score)
            }
        }

        if (state.isIntroVisible) {
            IntroOverlay(onStart = viewModel::dismissIntro)
        }

        if (state.isPaused) {
            PauseOverlay(
                onResume = { viewModel.resumeGame() },
                onMenu = {
                    viewModel.onExitToMenu()
                    onExit()
                },
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
                    modifier = Modifier.padding(top = 140.dp)
                )
            }
        }
    }

    BackHandler(enabled = !state.isPaused && !state.isGameOver) {
        viewModel.pauseGame()
    }
}

@Composable
private fun PauseButton(onClick: () -> Unit, enabled: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        RoundButton(
            icon = R.drawable.ic_settings,
            size = 64.dp,
            onClick = onClick,
            enabled = enabled
        )
    }
}

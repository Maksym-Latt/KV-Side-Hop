package com.chicken.sidehop.ui.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.chicken.sidehop.ui.components.ActionButton

@Composable
fun ResultScreen(
    score: Int,
    onRetry: () -> Unit,
    onMenu: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val bestScore = viewModel.bestScore.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.ensureBestScore(score)
        viewModel.onScreenVisible()
    }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.6f))

            OutlinedText(
                text = "WRONG PICK!",
                color = Color(0xfff1b75e),
                outline = Color(0xff4a2d11),
                fontSize = 42.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedText(
                text = "SCORE: $score",
                color = Color(0xfffdfdfd),
                outline = Color(0xffa17246),
                fontSize = 30.sp
            )

            OutlinedText(
                text = "MAX: ${bestScore.value}",
                color = Color(0xfffdfdfd),
                outline = Color(0xffa17246),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.weight(0.4f))

            Image(
                painter = painterResource(id = R.drawable.chicken_lose),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.62f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(0.7f))

            ActionButton(
                text = "TRY AGAIN",
                onClick = {
                    viewModel.onButtonTap()
                    onRetry()
                },
                style = ButtonStyle.Yellow,
                height = 84.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                text = "MENU",
                onClick = {
                    viewModel.onButtonTap()
                    onMenu()
                },
                style = ButtonStyle.Red,
                height = 84.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

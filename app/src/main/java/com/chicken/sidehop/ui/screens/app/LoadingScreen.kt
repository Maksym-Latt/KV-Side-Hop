package com.chicken.sidehop.ui.screens.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.theme.CreamPanel
import com.chicken.sidehop.ui.theme.OutlineDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(onFinished: () -> Unit) {
    var dots by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                dots = ""
                delay(300)
                dots = "."
                delay(300)
                dots = ".."
                delay(300)
                dots = "..."
                delay(300)
            }
        }
    }

    LaunchedEffect(Unit) {
        val duration = 1200L
        val steps = 60
        val stepDelay = duration / steps
        repeat(steps) {
            progress = (it + 1) / steps.toFloat()
            delay(stepDelay)
        }
        onFinished()
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
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.width(280.dp),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.weight(0.4f))

            Image(
                painter = painterResource(id = R.drawable.chicken),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.78f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(0.3f))

            OutlinedText(
                text = "LOADING$dots",
                color = Color(0xffeec520),
                outline = Color(0xFF5E3B1E),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF5E3B1E))
            ) {
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color(0xFFFBE27A))
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

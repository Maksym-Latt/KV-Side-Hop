package com.chicken.sidehop.ui.screens.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.theme.OutlineDark

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
        Image(
            painter = painterResource(id = R.drawable.chicken),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 12.dp, bottom = 48.dp)
                .size(180.dp),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.chicken),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 48.dp)
                .size(180.dp),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = 12.dp),
                contentScale = ContentScale.FillWidth
            )
            OutlinedText(
                text = "SIDE-HOP",
                color = MaterialTheme.colorScheme.secondary,
                outline = OutlineDark,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                text = "PLAY",
                height = 84.dp,
                onClick = onPlay,
                style = ButtonStyle.Yellow
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = "SETTINGS",
                height = 72.dp,
                onClick = onSettings,
                style = ButtonStyle.Red
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMenu() {
    MenuScreen(onPlay = {}, onSettings = {})
}

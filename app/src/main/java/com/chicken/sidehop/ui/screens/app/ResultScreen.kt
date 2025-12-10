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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chicken.sidehop.R
import com.chicken.sidehop.ui.components.ButtonStyle
import com.chicken.sidehop.ui.components.OutlinedText
import com.chicken.sidehop.ui.components.PanelCard
import com.chicken.sidehop.ui.components.PrimaryButton
import com.chicken.sidehop.ui.theme.OutlineDark
import com.chicken.sidehop.ui.theme.YellowDeep

@Composable
fun ResultScreen(
    score: Int,
    onRetry: () -> Unit,
    onMenu: () -> Unit
) {
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
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedText(
                text = "WRONG PICK!",
                color = MaterialTheme.colorScheme.secondary,
                outline = OutlineDark,
            )
            Image(
                painter = painterResource(id = R.drawable.chicken_lose),
                contentDescription = null,
                modifier = Modifier.height(180.dp),
                contentScale = ContentScale.Fit
            )
            PanelCard(modifier = Modifier.padding(horizontal = 12.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedText(
                        text = "SCORE: $score",
                        color = YellowDeep,
                        outline = OutlineDark,
                    )
                }
            }
            PrimaryButton(
                text = "TRY AGAIN",
                onClick = onRetry,
                style = ButtonStyle.Yellow
            )
            PrimaryButton(
                text = "MENU",
                onClick = onMenu,
                style = ButtonStyle.Red
            )
        }
    }
}

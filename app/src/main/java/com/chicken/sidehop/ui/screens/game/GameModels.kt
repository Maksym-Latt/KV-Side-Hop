package com.chicken.sidehop.ui.screens.game

import androidx.annotation.DrawableRes
import com.chicken.sidehop.R
import java.util.UUID

enum class Lane { LEFT, RIGHT }

enum class ItemType(@DrawableRes val icon: Int, val isGood: Boolean) {
    EGG(R.drawable.item_egg, true),
    APPLE(R.drawable.item_apple, true),
    SALAD(R.drawable.item_salad, true),
    ANVIL(R.drawable.item_anvil, false),
    BOOT(R.drawable.item_boot, false),
    TNT(R.drawable.item_tnt, false);

    companion object {
        fun randomGood(): ItemType = listOf(EGG, APPLE, SALAD).random()
        fun randomBad(): ItemType = listOf(ANVIL, BOOT, TNT).random()
    }
}

data class FallingItem(
    val id: String = UUID.randomUUID().toString(),
    val lane: Lane,
    val type: ItemType,
    val yProgress: Float = 0f,
    val speed: Float
)

data class GameUiState(
    val score: Int = 0,
    val isPaused: Boolean = false,
    val chickenLane: Lane = Lane.LEFT,
    val chickenJumpOffset: Float = 0f,
    val isJumping: Boolean = false,
    val jumpVelocity: Float = 0f,
    val items: List<FallingItem> = emptyList(),
    val isGameOver: Boolean = false,
    val showWrongPick: Boolean = false
)

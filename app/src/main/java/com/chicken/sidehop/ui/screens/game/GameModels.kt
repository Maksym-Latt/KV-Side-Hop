package com.chicken.sidehop.ui.screens.game

import androidx.annotation.DrawableRes
import com.chicken.sidehop.R
import java.util.UUID

enum class Lane { LEFT, RIGHT }

object LanePositions {
    const val LEFT = 0.24f
    const val RIGHT = 0.76f
    const val CENTER = (LEFT + RIGHT) / 2f
}

object CollisionConfig {
    const val CONTACT_THRESHOLD = 0.7f
    const val GROUND_RANGE = 0.12f
    const val CATCH_WIDTH = 0.12f
    const val JUMP_INFLUENCE = 0.2f
}

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
    val xPosition: Float,
    val type: ItemType,
    val yProgress: Float = 0f,
    val speed: Float
)

data class GameUiState(
    val score: Int = 0,
    val isPaused: Boolean = false,
    val chickenLane: Lane = Lane.LEFT,
    val chickenX: Float = LanePositions.LEFT,
    val chickenTargetX: Float = LanePositions.LEFT,
    val chickenJumpOffset: Float = 0f,
    val isJumping: Boolean = false,
    val jumpVelocity: Float = 0f,
    val items: List<FallingItem> = emptyList(),
    val isGameOver: Boolean = false,
    val showWrongPick: Boolean = false,
    val isIntroVisible: Boolean = true
)

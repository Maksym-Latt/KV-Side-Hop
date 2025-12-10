package com.chicken.sidehop.ui.screens.game

object FallingSpeedConfig {
    const val MIN_SPEED = 0.42f
    const val MAX_SPEED = 0.90f
    const val SPEED_GROWTH_PER_SCORE = 0.02f
    const val RANDOM_SPEED_JITTER = 0.15f
}

object SpawnTimingConfig {
    const val MIN_INTERVAL = 0.6f
    const val MAX_INTERVAL = 1.2f
    const val SPAWN_GROWTH_PER_SCORE = 0.05f
}

package com.chicken.sidehop.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.sidehop.core.audio.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var spawnTimer = 0f
    private val gravity = 4f

    init {
        audioController.playGameMusic()
        startGameLoop()
    }

    fun togglePause() {
        _uiState.update { it.copy(isPaused = !it.isPaused) }
    }

    fun pauseGame() {
        _uiState.update { it.copy(isPaused = true) }
    }

    fun resumeGame() {
        _uiState.update { it.copy(isPaused = false) }
    }

    fun onJump() {
        _uiState.update { state ->
            if (state.isPaused || state.isGameOver || state.isJumping) state
            else state.copy(isJumping = true, jumpVelocity = -2.3f)
        }
    }

    fun onSwitchLane() {
        _uiState.update { state ->
            if (state.isPaused || state.isGameOver) state
            else state.copy(chickenLane = if (state.chickenLane == Lane.LEFT) Lane.RIGHT else Lane.LEFT)
        }
    }

    fun restart() {
        spawnTimer = 0f
        _uiState.value = GameUiState()
        audioController.playGameMusic()
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            var lastTime = System.nanoTime()
            while (true) {
                val now = System.nanoTime()
                val delta = (now - lastTime) / 1_000_000_000f
                lastTime = now
                tick(delta)
                delay(16)
            }
        }
    }

    private fun tick(delta: Float) {
        val current = _uiState.value
        if (current.isPaused || current.isGameOver) return

        val difficulty = 1f + current.score * 0.05f
        spawnTimer -= delta
        val baseSpeed = 0.55f + (current.score * 0.03f)

        var items = current.items.map { item ->
            item.copy(yProgress = item.yProgress + delta * item.speed)
        }.filter { it.yProgress < 1.2f }

        val contactThreshold = 0.8f
        val groundRange = 0.12f

        val collected = items.filter { item ->
            item.lane == current.chickenLane && item.yProgress in contactThreshold..(contactThreshold + groundRange)
        }

        if (collected.isNotEmpty()) {
            val hasBad = collected.any { !it.type.isGood }
            items = items.filterNot { collected.map { c -> c.id }.contains(it.id) }
            if (hasBad) {
                audioController.playBadItem()
                audioController.playLose()
                _uiState.update {
                    it.copy(
                        isGameOver = true,
                        showWrongPick = true,
                        items = emptyList()
                    )
                }
                return
            } else {
                audioController.playGoodItem()
                _uiState.update { state ->
                    state.copy(score = state.score + collected.count { it.type.isGood })
                }
            }
        }

        if (spawnTimer <= 0f) {
            val comboChance = 0.25f + (current.score * 0.01f)
            val doubleBonus = current.score >= 6 && Random.nextFloat() < 0.15f
            val newItems = mutableListOf<FallingItem>()
            if (Random.nextFloat() < comboChance) {
                newItems += createItem(Lane.LEFT, ItemType.randomGood(), baseSpeed)
                newItems += createItem(Lane.RIGHT, ItemType.randomBad(), baseSpeed + 0.12f)
            } else if (doubleBonus) {
                newItems += createItem(Lane.LEFT, ItemType.EGG, baseSpeed)
                newItems += createItem(Lane.RIGHT, ItemType.EGG, baseSpeed)
            } else {
                val lane = if (Random.nextBoolean()) Lane.LEFT else Lane.RIGHT
                val type = if (Random.nextFloat() > 0.3f) ItemType.randomGood() else ItemType.randomBad()
                newItems += createItem(lane, type, baseSpeed)
            }
            items = items + newItems
            spawnTimer = max(0.6f, 1.2f / difficulty)
        }

        var jumpVelocity = current.jumpVelocity
        var jumpOffset = current.chickenJumpOffset
        var isJumping = current.isJumping

        if (isJumping) {
            jumpOffset += jumpVelocity * delta
            jumpVelocity += gravity * delta
            if (jumpOffset >= 0f) {
                jumpOffset = 0f
                jumpVelocity = 0f
                isJumping = false
            }
        }

        _uiState.update {
            it.copy(
                items = items,
                chickenJumpOffset = jumpOffset,
                jumpVelocity = jumpVelocity,
                isJumping = isJumping
            )
        }
    }

    private fun createItem(lane: Lane, type: ItemType, baseSpeed: Float): FallingItem {
        val speed = baseSpeed + Random.nextFloat() * 0.5f
        return FallingItem(lane = lane, type = type, speed = speed)
    }
}

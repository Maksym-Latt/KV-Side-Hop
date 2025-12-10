package com.chicken.sidehop.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.sidehop.core.audio.AudioController
import com.chicken.sidehop.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val audioController: AudioController,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var spawnTimer = 0f
    private val gravity = 3.4f

    init {
        audioController.playGameMusic()
        startGameLoop()
    }

    fun togglePause() {
        audioController.playTap()
        _uiState.update { it.copy(isPaused = !it.isPaused) }
    }

    fun pauseGame() {
        audioController.playTap()
        _uiState.update { it.copy(isPaused = true) }
    }

    fun resumeGame() {
        audioController.playTap()
        _uiState.update { it.copy(isPaused = false) }
    }

    fun onExitToMenu() {
        audioController.playTap()
        viewModelScope.launch {
            settingsRepository.updateBestScoreIfHigher(_uiState.value.score)
        }
        audioController.playMenuMusic()
    }

    fun onJump() {
        _uiState.update { state ->
            if (state.isPaused || state.isGameOver || state.isJumping || state.isIntroVisible) state
            else {
                audioController.playSlide()
                state.copy(isJumping = true, jumpVelocity = -2.3f)
            }
        }
    }

    fun onSwitchLane() {
        _uiState.update { state ->
            if (state.isPaused || state.isGameOver || state.isIntroVisible) state
            else {
                audioController.playSlide()
                val newLane = if (state.chickenLane == Lane.LEFT) Lane.RIGHT else Lane.LEFT
                val targetX = if (newLane == Lane.LEFT) LanePositions.LEFT else LanePositions.RIGHT
                state.copy(chickenLane = newLane, chickenTargetX = targetX)
            }
        }
    }

    fun dismissIntro() {
        _uiState.update {
            audioController.playTap()
            it.copy(isIntroVisible = false)
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
        if (current.isPaused || current.isGameOver || current.isIntroVisible) return

        val difficulty = 1f + current.score * SpawnTimingConfig.SPAWN_GROWTH_PER_SCORE
        spawnTimer -= delta
        val baseSpeed = (
            FallingSpeedConfig.MIN_SPEED + (current.score * FallingSpeedConfig.SPEED_GROWTH_PER_SCORE)
            ).coerceAtMost(FallingSpeedConfig.MAX_SPEED)

        var items = current.items.map { item ->
            item.copy(yProgress = item.yProgress + delta * item.speed)
        }.filter { it.yProgress < 1.2f }

        val baseContact = CollisionConfig.CONTACT_THRESHOLD
        val verticalContact = (baseContact + current.chickenJumpOffset * CollisionConfig.JUMP_INFLUENCE)
            .coerceIn(0f, 1f)
        val contactThreshold = verticalContact
        val groundRange = CollisionConfig.GROUND_RANGE
        val catchWidth = CollisionConfig.CATCH_WIDTH

        val collected = items.filter { item ->
            abs(item.xPosition - current.chickenX) <= catchWidth &&
                item.yProgress in contactThreshold..(contactThreshold + groundRange).coerceAtMost(1f)
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
                viewModelScope.launch {
                    settingsRepository.updateBestScoreIfHigher(current.score)
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
                newItems += createItem(ItemType.randomGood(), baseSpeed)
                newItems += createItem(ItemType.randomBad(), baseSpeed + 0.12f)
            } else if (doubleBonus) {
                newItems += createItem(ItemType.EGG, baseSpeed)
                newItems += createItem(ItemType.EGG, baseSpeed)
            } else {
                val type = if (Random.nextFloat() > 0.3f) ItemType.randomGood() else ItemType.randomBad()
                newItems += createItem(type, baseSpeed)
            }
            items = items + newItems
            spawnTimer = max(
                SpawnTimingConfig.MIN_INTERVAL,
                SpawnTimingConfig.MAX_INTERVAL / difficulty
            )
        }

        var chickenX = current.chickenX
        val targetX = current.chickenTargetX
        val runSpeed = 2.6f

        if (abs(targetX - chickenX) > 0.001f) {
            val direction = sign(targetX - chickenX)
            val step = runSpeed * delta * direction
            chickenX = if (abs(targetX - chickenX) <= abs(step)) targetX else chickenX + step
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
                chickenX = chickenX,
                chickenJumpOffset = jumpOffset,
                jumpVelocity = jumpVelocity,
                isJumping = isJumping
            )
        }
    }

    private fun createItem(type: ItemType, baseSpeed: Float): FallingItem {
        val baseLane = when (Random.nextInt(3)) {
            0 -> LanePositions.LEFT
            1 -> LanePositions.CENTER
            else -> LanePositions.RIGHT
        }
        val jitter = (Random.nextFloat() - 0.5f) * 0.08f
        val spawnX = (baseLane + jitter).coerceIn(0.1f, 0.9f)
        val speed = (baseSpeed + Random.nextFloat() * FallingSpeedConfig.RANDOM_SPEED_JITTER)
            .coerceAtMost(FallingSpeedConfig.MAX_SPEED)
        return FallingItem(xPosition = spawnX, type = type, speed = speed)
    }
}

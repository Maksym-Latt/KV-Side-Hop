package com.chicken.sidehop.core.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.chicken.sidehop.R
import com.chicken.sidehop.data.settings.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface AudioController {
    val isSoundEnabled: StateFlow<Boolean>
    val isMusicEnabled: StateFlow<Boolean>
    fun setSoundEnabled(enabled: Boolean)
    fun setMusicEnabled(enabled: Boolean)
    fun playMenuMusic()
    fun playGameMusic()
    fun stopMusic()
    fun playGoodItem()
    fun playBadItem()
    fun playLose()
    fun onAppForeground()
    fun onAppBackground()
}

@Singleton
class MediaAudioController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) : AudioController {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _isSoundEnabled = MutableStateFlow(true)
    private val _isMusicEnabled = MutableStateFlow(true)
    override val isSoundEnabled: StateFlow<Boolean> = _isSoundEnabled
    override val isMusicEnabled: StateFlow<Boolean> = _isMusicEnabled

    private var menuPlayer: MediaPlayer? = null
    private var gamePlayer: MediaPlayer? = null
    private var currentMusic: MediaPlayer? = null

    private var isInForeground = true

    init {
        scope.launch {
            settingsRepository.isSoundEnabled.collectLatest { enabled ->
                _isSoundEnabled.value = enabled
            }
        }
        scope.launch {
            settingsRepository.isMusicEnabled.collectLatest { enabled ->
                _isMusicEnabled.value = enabled
                if (!enabled) {
                    stopMusic()
                } else if (isInForeground) {
                    currentMusic?.start()
                }
            }
        }
    }

    override fun setSoundEnabled(enabled: Boolean) {
        scope.launch { settingsRepository.updateSound(enabled) }
    }

    override fun setMusicEnabled(enabled: Boolean) {
        scope.launch { settingsRepository.updateMusic(enabled) }
    }

    override fun playMenuMusic() {
        if (!isMusicEnabled.value) return
        if (menuPlayer == null) {
            menuPlayer = createLoopingPlayer(R.raw.menu_music)
        }
        switchMusic(menuPlayer)
    }

    override fun playGameMusic() {
        if (!isMusicEnabled.value) return
        if (gamePlayer == null) {
            gamePlayer = createLoopingPlayer(R.raw.game_music)
        }
        switchMusic(gamePlayer)
    }

    override fun stopMusic() {
        currentMusic?.pause()
        currentMusic?.seekTo(0)
    }

    override fun playGoodItem() {
        playEffect(R.raw.sfx_good_item)
    }

    override fun playBadItem() {
        playEffect(R.raw.sfx_bad_item)
    }

    override fun playLose() {
        playEffect(R.raw.sfx_lose)
    }

    override fun onAppForeground() {
        isInForeground = true
        if (isMusicEnabled.value) {
            currentMusic?.start()
        }
    }

    override fun onAppBackground() {
        isInForeground = false
        currentMusic?.pause()
    }

    private fun switchMusic(target: MediaPlayer?) {
        if (currentMusic == target) {
            if (isMusicEnabled.value && isInForeground) currentMusic?.start()
            return
        }
        currentMusic?.pause()
        currentMusic = target
        if (isMusicEnabled.value && isInForeground) {
            currentMusic?.start()
        }
    }

    private fun createLoopingPlayer(@RawRes resId: Int): MediaPlayer =
        MediaPlayer.create(context, resId).apply { isLooping = true }

    private fun playEffect(@RawRes resId: Int) {
        if (!isSoundEnabled.value) return
        MediaPlayer.create(context, resId).apply {
            setOnCompletionListener { release() }
            start()
        }
    }
}

package com.chicken.sidehop.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.sidehop.core.audio.AudioController
import com.chicken.sidehop.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val audioController: AudioController
) : ViewModel() {

    val bestScore: StateFlow<Int> = settingsRepository.bestScore.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    fun onScreenVisible() {
        audioController.playMenuMusic()
    }

    fun onButtonTap() {
        audioController.playTap()
    }

    fun ensureBestScore(score: Int) {
        viewModelScope.launch {
            settingsRepository.updateBestScoreIfHigher(score)
        }
    }
}

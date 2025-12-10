package com.chicken.sidehop.ui.screens.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.sidehop.core.audio.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {

    val isSoundEnabled: StateFlow<Boolean> = audioController.isSoundEnabled
    val isMusicEnabled: StateFlow<Boolean> = audioController.isMusicEnabled

    fun onSoundToggle(enabled: Boolean) {
        viewModelScope.launch { audioController.setSoundEnabled(enabled) }
    }

    fun onMusicToggle(enabled: Boolean) {
        viewModelScope.launch {
            audioController.setMusicEnabled(enabled)
            if (enabled) audioController.playMenuMusic()
        }
    }
}

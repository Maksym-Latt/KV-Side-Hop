package com.chicken.sidehop.ui.screens.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.sidehop.core.audio.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {

    init {
        audioController.playMenuMusic()
    }

    fun onMenuVisible() {
        audioController.playMenuMusic()
    }

    fun toggleMusic(enabled: Boolean) {
        audioController.setMusicEnabled(enabled)
        if (enabled) audioController.playMenuMusic()
    }

    fun toggleSound(enabled: Boolean) {
        audioController.setSoundEnabled(enabled)
    }

    fun onButtonTap() {
        audioController.playTap()
    }
}

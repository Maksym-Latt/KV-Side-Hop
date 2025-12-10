package com.chicken.sidehop.di

import com.chicken.sidehop.core.audio.AudioController
import com.chicken.sidehop.core.audio.MediaAudioController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindAudioController(impl: MediaAudioController): AudioController
}

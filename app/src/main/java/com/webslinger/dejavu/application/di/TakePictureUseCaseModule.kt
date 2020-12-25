package com.webslinger.dejavu.application.di

import android.content.Context
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.domain.ICamera
import dagger.Module
import dagger.Provides

@Module
class TakePictureUseCaseModule {
    @Provides
    fun providesTakePictureUseCase(context: Context): TakePictureUseCase {
        return TakePictureUseCase(context)
    }
}
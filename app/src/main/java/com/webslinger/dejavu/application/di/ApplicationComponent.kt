package com.webslinger.dejavu.application.di

import android.content.Context
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.application.viewmodel.TakeAfterPictureViewModelFactory
import com.webslinger.dejavu.domain.ICamera
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        CameraModule::class,
        TakePictureUseCaseModule::class,
        TakeAfterPictureViewModelFactoryModule::class
    ]
)
interface ApplicationComponent {
    fun getContext(): Context

    @Singleton
    fun getCamera(): ICamera

    fun getTakeAfterPictureViewModelFactory(): TakeAfterPictureViewModelFactory
}
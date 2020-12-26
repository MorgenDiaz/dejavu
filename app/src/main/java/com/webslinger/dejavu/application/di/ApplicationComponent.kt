package com.webslinger.dejavu.application.di

import android.content.Context
import com.webslinger.dejavu.domain.ICamera
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        CameraModule::class,
        TakeAfterPictureViewModelFactoryModule::class,
        TakePictureUseCaseModule::class,
    ]
)
@Singleton
interface ApplicationComponent {
    fun getContext(): Context

    @Singleton
    fun getCamera(): ICamera

    fun takeAfterPictureComponent(): TakeAfterPictureComponent
}
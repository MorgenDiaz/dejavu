package com.webslinger.dejavu.application.di

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import com.webslinger.dejavu.application.infrastructure.camera.DefaultCamera
import com.webslinger.dejavu.application.infrastructure.camera.DefaultImageCaptureConfiguration
import com.webslinger.dejavu.application.infrastructure.camera.DefaultPreviewConfiguration
import com.webslinger.dejavu.domain.ICamera
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CameraModule {

    @Provides
    @Singleton
    fun providesCamera(context: Context): ICamera{
        return DefaultCamera(
            ProcessCameraProvider.getInstance(context.applicationContext),
            DefaultPreviewConfiguration(),
            DefaultImageCaptureConfiguration(),
        )
    }
}
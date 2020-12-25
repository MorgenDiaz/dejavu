package com.webslinger.dejavu.application.di

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import com.webslinger.dejavu.application.infrastructure.camera.DefaultCamera
import com.webslinger.dejavu.application.infrastructure.camera.DefaultImageCaptureConfiguration
import com.webslinger.dejavu.application.infrastructure.camera.DefaultPreviewConfiguration
import com.webslinger.dejavu.domain.ICamera
import dagger.Module
import dagger.Provides

@Module
class CameraModule {

    @Provides
    fun providesCamera(context: Context): ICamera{
        return DefaultCamera(
            ProcessCameraProvider.getInstance(context),
            DefaultPreviewConfiguration(),
            DefaultImageCaptureConfiguration(),
        )
    }
}
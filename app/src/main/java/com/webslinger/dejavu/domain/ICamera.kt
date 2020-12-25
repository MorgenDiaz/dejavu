package com.webslinger.dejavu.domain

import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

interface ICamera {
    fun start(
        executor: Executor,
        lifeCycleOwner: LifecycleOwner,
        previewSurface: Preview.SurfaceProvider
    )
    fun takePhoto(
        outputOptions: ImageCapture.OutputFileOptions,
        executor: Executor,
        onImageSavedCallback: ImageCapture.OnImageSavedCallback
    )
}
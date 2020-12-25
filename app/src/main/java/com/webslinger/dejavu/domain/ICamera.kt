package com.webslinger.dejavu.domain

import androidx.camera.core.ImageCapture
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

interface ICamera {
    fun start(executor: Executor)
    fun takePhoto(
        outputOptions: ImageCapture.OutputFileOptions,
        executor: Executor,
        onImageSavedCallback: ImageCapture.OnImageSavedCallback
    )
}
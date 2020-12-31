package com.webslinger.dejavu.application.infrastructure.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.webslinger.dejavu.domain.ICamera
import com.webslinger.dejavu.domain.IImageCaptureConfiguration
import com.webslinger.dejavu.domain.IPreviewConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executor

class DefaultCamera(
    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    private val previewConfiguration: IPreviewConfiguration,
    private val imageCaptureConfiguration: IImageCaptureConfiguration,
) : ICamera {
    private val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
    private var imageCapture: ImageCapture = imageCaptureConfiguration.configure()
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun start(
        executor: Executor,
        lifeCycleOwner: LifecycleOwner,
        previewSurface: Preview.SurfaceProvider
    ) {
        cameraProviderFuture.addListener(
            Runnable {
                try {
                    bindPreview(
                        lifeCycleOwner,
                        previewConfiguration.configure(previewSurface)
                    )
                } catch (exc: Exception) {
                    Timber.e(exc, "Use case binding failed")
                }
            }, executor
        )
    }

    private fun bindPreview(lifeCycleOwner: LifecycleOwner, preview: Preview) {
        // Unbind use cases before rebinding
        cameraProvider.unbindAll()

        // Bind use cases to camera
        cameraProvider.bindToLifecycle(
            lifeCycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    override fun takePhoto(
        outputOptions: ImageCapture.OutputFileOptions,
        executor: Executor,
        onImageSavedCallback: ImageCapture.OnImageSavedCallback
    ) {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            outputOptions,
            executor,
            onImageSavedCallback
        )
    }

    override fun stop() {
        cameraProvider.unbindAll()
    }
}
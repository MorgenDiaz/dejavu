package com.webslinger.dejavu.application.infrastructure.camera

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.webslinger.dejavu.domain.ICamera
import com.webslinger.dejavu.domain.IImageCaptureConfiguration
import com.webslinger.dejavu.domain.IPreviewConfiguration
import com.webslinger.dejavu.presentation.main.TakeAfterPictureFragment
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class DefaultCamera(
    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    private val previewConfiguration: IPreviewConfiguration,
    private val imageCaptureConfiguration: IImageCaptureConfiguration,
): ICamera {
    private val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
    private var imageCapture: ImageCapture = imageCaptureConfiguration.configure()
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun start(
        executor: Executor,
        lifeCycleOwner: LifecycleOwner,
        previewSurface: Preview.SurfaceProvider
    ) {
        cameraProviderFuture.addListener(Runnable {
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifeCycleOwner,
                    cameraSelector,
                    previewConfiguration.configure(previewSurface),
                    imageCapture
                )

            } catch(exc: Exception) {
                Timber.e(exc, "Use case binding failed")
            }

        }, executor)
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
}
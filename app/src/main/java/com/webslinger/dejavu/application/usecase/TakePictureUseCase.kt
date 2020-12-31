package com.webslinger.dejavu.application.usecase

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.webslinger.dejavu.domain.ICamera
import timber.log.Timber
import java.io.File

class TakePictureUseCase(
    private val context: Context,
    ){

    interface OnImageCapturedCallback{
        fun onImageCaptureSuccess(photoUri: Uri)
        fun onImageCaptureFailure(imageCaptureException: ImageCaptureException)
    }

    fun execute(
        camera: ICamera,
        photoFile: File,
        onImageCapturedCallback: OnImageCapturedCallback
    )
    {

        camera.takePhoto(
            ImageCapture.OutputFileOptions.Builder(photoFile).build(),
            ContextCompat.getMainExecutor(context),

            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e(exc, "Photo capture failed: ${exc.message}")
                    onImageCapturedCallback.onImageCaptureFailure(exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savePhoto(photoFile)
                    onImageCapturedCallback.onImageCaptureSuccess(Uri.fromFile(photoFile))
                }
            }
        )
    }

    private fun savePhoto(photoFile: File) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(photoFile.path),
            arrayOf("image/jpeg"),
            null
        )
    }
}
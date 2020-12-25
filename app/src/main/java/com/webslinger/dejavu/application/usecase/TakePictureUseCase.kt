package com.webslinger.dejavu.application.usecase

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.webslinger.dejavu.domain.ICamera
import timber.log.Timber
import java.io.File

class TakePictureUseCase(
    private val context: Context,
    ){
    fun execute(camera: ICamera, photoFile: File) {
        camera.takePhoto(
            ImageCapture.OutputFileOptions.Builder(photoFile).build(),
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e(exc, "Photo capture failed: ${exc.message}")

                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    MediaScannerConnection.scanFile(
                        context,
                        arrayOf(photoFile.path),
                        arrayOf("image/jpeg"),
                        null
                    )
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Timber.d(msg)
                }
            }
        )
    }
}
package com.webslinger.dejavu.application.viewmodel

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.infrastructure.PhotoOutputDirectoryProvider
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.domain.ICamera
import com.webslinger.dejavu.presentation.main.TakeAfterPictureFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class TakeAfterPictureViewModel(
    private val takePictureUseCase: TakePictureUseCase,
    private val outputDirectoryProvider: PhotoOutputDirectoryProvider,
    private val camera: ICamera,
    private val executor: Executor
    ) : ViewModel() {

    fun startCameraPreview(lifecycleOwner: LifecycleOwner, surfaceProvider: Preview.SurfaceProvider){
        camera.start(
            executor,
            lifecycleOwner,
            surfaceProvider
        )
    }

    fun takeAfterPicture(){
        val photoFile = File(
            outputDirectoryProvider.getOutputDirectory(),
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val photoUri = takePictureUseCase.execute(
            camera,
            photoFile,
        )
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

}
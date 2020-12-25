package com.webslinger.dejavu.application.viewmodel

import android.content.Context
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.domain.ICamera
import com.webslinger.dejavu.presentation.main.TakeAfterPictureFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakeAfterPictureViewModel( private val takePictureUseCase: TakePictureUseCase) : ViewModel() {
    fun takeAfterPicture(camera: ICamera, outputDirectory: File){
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        takePictureUseCase.execute(camera, photoFile)
    }
    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

}
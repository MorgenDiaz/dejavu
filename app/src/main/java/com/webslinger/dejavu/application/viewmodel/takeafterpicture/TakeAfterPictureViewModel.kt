package com.webslinger.dejavu.application.viewmodel.takeafterpicture

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.webslinger.dejavu.application.infrastructure.PhotoOutputDirectoryProvider
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.application.viewmodel.common.ScreenNavigator
import com.webslinger.dejavu.domain.ICamera
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

    private val _photoUri: MutableLiveData<Uri> = MutableLiveData()
    val photoUri: LiveData<Uri> = _photoUri

    private val _beforeStencilOpacity: MutableLiveData<Int> = MutableLiveData(50)
    val beforeStencilOpacity: LiveData<Int> = _beforeStencilOpacity

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

        takePictureUseCase.execute(
            camera,
            photoFile,
            onImageSavedHandler
        )

    }

    private val onImageSavedHandler = object : TakePictureUseCase.OnImageCapturedCallback{
        override fun onImageCaptureSuccess(photoUri: Uri) {
            _photoUri.value = photoUri
        }

        override fun onImageCaptureFailure(imageCaptureException: ImageCaptureException) {
            TODO("Not yet implemented")
        }
    }

    fun increaseOpacity(){
        _beforeStencilOpacity.value = _beforeStencilOpacity.value!! + 10
    }

    fun decreaseOpacity(){
        _beforeStencilOpacity.value = _beforeStencilOpacity.value!! - 10
    }

    fun navigateBack(screenNavigator: ScreenNavigator){
        screenNavigator.navigateBack()
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

}
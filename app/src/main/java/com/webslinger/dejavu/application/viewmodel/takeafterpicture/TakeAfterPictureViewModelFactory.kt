package com.webslinger.dejavu.application.viewmodel.takeafterpicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webslinger.dejavu.application.infrastructure.PhotoOutputDirectoryProvider
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.domain.ICamera
import java.util.concurrent.Executor

class TakeAfterPictureViewModelFactory(
    private val takePictureUseCase: TakePictureUseCase,
    private val photoOutputDirectoryProvider: PhotoOutputDirectoryProvider,
    private val camera: ICamera,
    private val executor: Executor
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeAfterPictureViewModel::class.java)){
            return TakeAfterPictureViewModel(
                takePictureUseCase,
                photoOutputDirectoryProvider,
                camera,
                executor,

                ) as T
        }else{
            throw Error("Invalid viewmodel class")
        }
    }
}
package com.webslinger.dejavu.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webslinger.dejavu.application.usecase.TakePictureUseCase

class TakeAfterPictureViewModelFactory(private val takePictureUseCase: TakePictureUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeAfterPictureViewModel::class.java)){
            return TakeAfterPictureViewModel(takePictureUseCase) as T
        }else{
            throw Error("Invalid viewmodel class")
        }
    }
}
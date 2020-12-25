package com.webslinger.dejavu.application.di

import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.application.viewmodel.TakeAfterPictureViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TakeAfterPictureViewModelFactoryModule {
    @Provides
    fun providesTakeAfterPictureViewModelFactory(takePictureUseCase: TakePictureUseCase): TakeAfterPictureViewModelFactory{
        return TakeAfterPictureViewModelFactory(takePictureUseCase)
    }
}
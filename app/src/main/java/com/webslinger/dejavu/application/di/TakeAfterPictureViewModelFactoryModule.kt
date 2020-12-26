package com.webslinger.dejavu.application.di

import android.content.Context
import androidx.core.content.ContextCompat
import com.webslinger.dejavu.application.infrastructure.PhotoOutputDirectoryProvider
import com.webslinger.dejavu.application.usecase.TakePictureUseCase
import com.webslinger.dejavu.application.viewmodel.TakeAfterPictureViewModelFactory
import com.webslinger.dejavu.domain.ICamera
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

@Module
@TakeAfterPhotoScope
class TakeAfterPictureViewModelFactoryModule {
    @Provides
    fun providesTakeAfterPictureViewModelFactory(
        takePictureUseCase: TakePictureUseCase,
        context: Context,
        camera: ICamera
    ): TakeAfterPictureViewModelFactory{
        return TakeAfterPictureViewModelFactory(
            takePictureUseCase,
            PhotoOutputDirectoryProvider(context),
            camera,
            ContextCompat.getMainExecutor(context)
        )
    }
}
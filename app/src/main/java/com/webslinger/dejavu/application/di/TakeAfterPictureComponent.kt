package com.webslinger.dejavu.application.di

import com.webslinger.dejavu.presentation.fragment.TakeAfterPictureFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    TakeAfterPictureViewModelFactoryModule::class,
    TakePictureUseCaseModule::class,
]
)
@TakeAfterPhotoScope
interface TakeAfterPictureComponent {
    fun inject(takeAfterPictureFragment: TakeAfterPictureFragment)
}
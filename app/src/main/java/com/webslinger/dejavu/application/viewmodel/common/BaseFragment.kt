package com.webslinger.dejavu.application.viewmodel.common

import androidx.fragment.app.Fragment
import com.webslinger.dejavu.application.DejavuApplication
import com.webslinger.dejavu.presentation.main.TakeAfterPictureFragment

abstract class BaseFragment: Fragment() {
    private val applicationComponent by lazy{
        (requireActivity().application as DejavuApplication).applicationComponent
    }

    fun <T: Fragment>inject(fragmentClass: T){
        if(fragmentClass::class == TakeAfterPictureFragment::class){
            applicationComponent.takeAfterPictureComponent().inject(fragmentClass as TakeAfterPictureFragment)
        }
    }
}
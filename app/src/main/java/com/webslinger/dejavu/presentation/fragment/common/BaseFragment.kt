package com.webslinger.dejavu.presentation.fragment.common

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.webslinger.dejavu.application.DejavuApplication
import com.webslinger.dejavu.application.viewmodel.common.ScreenNavigator
import com.webslinger.dejavu.presentation.fragment.TakeAfterPictureFragment

abstract class BaseFragment: Fragment() {
    private val applicationComponent by lazy{
        (requireActivity().application as DejavuApplication).applicationComponent
    }

    protected val screenNavigator:ScreenNavigator by lazy{
        ScreenNavigator(findNavController())
    }

    fun <T: Fragment>inject(fragmentClass: T){
        if(fragmentClass::class == TakeAfterPictureFragment::class){
            applicationComponent.takeAfterPictureComponent().inject(fragmentClass as TakeAfterPictureFragment)
        }
    }
}
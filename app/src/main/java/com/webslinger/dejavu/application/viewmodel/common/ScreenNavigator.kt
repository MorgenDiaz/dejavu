package com.webslinger.dejavu.application.viewmodel.common

import androidx.navigation.NavController

class ScreenNavigator(private val navigationController: NavController){
    fun navigateBack(){
        navigationController.popBackStack()
    }
}
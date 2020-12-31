package com.webslinger.dejavu.application.di

import android.content.Context
import androidx.navigation.Navigation
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun providesContext(): Context{
        return context
    }
}
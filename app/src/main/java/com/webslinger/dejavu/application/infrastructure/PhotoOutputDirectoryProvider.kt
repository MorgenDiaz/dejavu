package com.webslinger.dejavu.application.infrastructure

import android.content.Context
import com.webslinger.dejavu.R
import java.io.File

class PhotoOutputDirectoryProvider(private val context: Context) {
    fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }
}
package com.webslinger.dejavu.domain

import androidx.camera.core.ImageCapture

interface IImageCaptureConfiguration {
    fun configure(): ImageCapture
}
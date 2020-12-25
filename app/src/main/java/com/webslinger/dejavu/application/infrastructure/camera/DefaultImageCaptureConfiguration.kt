package com.webslinger.dejavu.application.infrastructure.camera

import androidx.camera.core.ImageCapture
import com.webslinger.dejavu.domain.IImageCaptureConfiguration

class DefaultImageCaptureConfiguration: IImageCaptureConfiguration {
    override fun configure(): ImageCapture {
        return ImageCapture.Builder()
            .build()
    }
}
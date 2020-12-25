package com.webslinger.dejavu.application.infrastructure.camera

import androidx.camera.core.Preview
import com.webslinger.dejavu.domain.IPreviewConfiguration

class DefaultPreviewConfiguration: IPreviewConfiguration {
    override fun configure(previewSurface: Preview.SurfaceProvider): Preview {
        return Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewSurface)
            }
    }

}
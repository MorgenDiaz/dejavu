package com.webslinger.dejavu.domain

import androidx.camera.core.Preview

interface IPreviewConfiguration {
    fun configure(previewSurface: Preview.SurfaceProvider): Preview
}
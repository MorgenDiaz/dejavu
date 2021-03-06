package com.webslinger.dejavu.application.viewmodel.selectbeforepicture

import android.content.Intent
import androidx.lifecycle.ViewModel


class SelectBeforePictureViewModel : ViewModel() {


    fun selectPictureFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        /*
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_GET_SINGLE_FILE
        )*/
    }
}
package com.webslinger.dejavu.presentation.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.viewmodel.takeafterpicture.TakeAfterPictureViewModel
import com.webslinger.dejavu.application.viewmodel.takeafterpicture.TakeAfterPictureViewModelFactory
import com.webslinger.dejavu.presentation.fragment.common.BaseFragment
import com.webslinger.dejavu.databinding.TakeAfterPictureFragmentBinding
import javax.inject.Inject

class TakeAfterPictureFragment : BaseFragment() {
    private lateinit var dataBinding: TakeAfterPictureFragmentBinding
    private lateinit var viewModel: TakeAfterPictureViewModel

    @Inject
    lateinit var viewModelFactory: TakeAfterPictureViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.take_after_picture_fragment,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject(this)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(TakeAfterPictureViewModel::class.java)

        loadBeforePicture()
        checkCameraPermissions()
        bindCameraCaptureButton()
    }

    private fun loadBeforePicture() {
        arguments?.let {
            val beforePictureUri: Uri = it.get("BEFORE_PICTURE_PATH") as Uri

            Glide.with(this)
                .load(beforePictureUri)
                .into(dataBinding.beforePictureOverlay)
        }

        dataBinding.beforePictureOverlay.imageAlpha = 70
    }

    private fun checkCameraPermissions() {
        if (allPermissionsGranted()) {
            viewModel.startCameraPreview(this, dataBinding.viewFinder.surfaceProvider)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun bindCameraCaptureButton() {
        dataBinding.cameraCaptureButton.setOnClickListener {
            viewModel.takeAfterPicture()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel.startCameraPreview(this, dataBinding.viewFinder.surfaceProvider)
            } else {
                showPermissionsNotGrantedMessage()
                findNavController().popBackStack()
            }
        }
    }

    private fun showPermissionsNotGrantedMessage() {
        Toast.makeText(
            requireContext(),
            "Permissions not granted by the user.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance() = TakeAfterPictureFragment()
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
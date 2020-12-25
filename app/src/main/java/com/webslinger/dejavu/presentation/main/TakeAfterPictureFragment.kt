package com.webslinger.dejavu.presentation.main


import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.infrastructure.camera.DefaultCamera
import com.webslinger.dejavu.application.infrastructure.camera.DefaultImageCaptureConfiguration
import com.webslinger.dejavu.application.infrastructure.camera.DefaultPreviewConfiguration
import com.webslinger.dejavu.application.viewmodel.TakeAfterPictureViewModel
import com.webslinger.dejavu.databinding.TakeAfterPictureFragmentBinding
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TakeAfterPictureFragment : Fragment() {
    private lateinit var dataBinding: TakeAfterPictureFragmentBinding
    private lateinit var viewModel: TakeAfterPictureViewModel

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private val camera: DefaultCamera by lazy {
        DefaultCamera(
            ProcessCameraProvider.getInstance(requireContext()),
            dataBinding.viewFinder.surfaceProvider,
            DefaultPreviewConfiguration(),
            DefaultImageCaptureConfiguration(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.take_after_picture_fragment,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TakeAfterPictureViewModel::class.java)
        // TODO: Use the ViewModel
        arguments?.let {
            val beforePictureUri: Uri = it.get("BEFORE_PICTURE_PATH") as Uri
            Glide.with(this)
                .load(beforePictureUri)
                .into(dataBinding.beforePictureOverlay)
        }

        dataBinding.beforePictureOverlay.imageAlpha = 50

        if (allPermissionsGranted()) {
            camera.start(ContextCompat.getMainExecutor(requireContext()))
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
        dataBinding.cameraCaptureButton.setOnClickListener {
            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US
                ).format(System.currentTimeMillis()) + ".jpg")

            camera.takePhoto(
                ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Timber.e(exc, "Photo capture failed: ${exc.message}")

                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val savedUri = Uri.fromFile(photoFile)
                        MediaScannerConnection.scanFile(requireContext(), arrayOf(photoFile.path), arrayOf("image/jpeg" ), null)
                        val msg = "Photo capture succeeded: $savedUri"
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Timber.d(msg)
                    }
                }
            )
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                camera.start(ContextCompat.getMainExecutor(requireContext()))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    companion object {
        fun newInstance() = TakeAfterPictureFragment()

        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
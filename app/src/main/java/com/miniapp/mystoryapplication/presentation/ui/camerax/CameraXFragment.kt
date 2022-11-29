package com.miniapp.mystoryapplication.presentation.ui.camerax

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.databinding.FragmentCameraXBinding
import com.miniapp.mystoryapplication.presentation.utils.allPermissionsGranted
import com.miniapp.mystoryapplication.presentation.utils.createFile
import com.miniapp.mystoryapplication.presentation.utils.requestSinglePermission
import com.miniapp.mystoryapplication.presentation.utils.showToast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import timber.log.Timber

class CameraXFragment : BaseFragment(R.layout.fragment_camera_x) {

    private val binding: FragmentCameraXBinding by viewBinding(FragmentCameraXBinding::bind)

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var safeContext: Context
    private lateinit var cameraExecutor: ExecutorService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
        checkPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private fun setupView() {
        binding.captureImage.setOnClickListener { takePicture() }
        binding.switchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA

            startCamera()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkPermission() {
        if (allPermissionsGranted(safeContext, REQUIRED_PERMISSIONS)) {
            startCamera()
        } else {
            requestSinglePermission(Manifest.permission.CAMERA) {
                if (it) startCamera()
                else showToast(getString(R.string.camera_failed_to_start))
            }
        }
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Timber.e(exc.toString())
            }

        }, ContextCompat.getMainExecutor(safeContext))

    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    findNavController().navigate(
                        CameraXFragmentDirections.actionCameraXFragmentToNewStoryFragment()
                            .setFile(photoFile)
                            .setIsBackCamera(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                    )
                }

                override fun onError(exception: ImageCaptureException) {
                    showToast(getString(R.string.camera_failed_to_capture))
                }
            }
        )
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        requireActivity().actionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
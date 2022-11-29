package com.miniapp.mystoryapplication.presentation.ui.newstory

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.databinding.FragmentNewStoryBinding
import com.miniapp.mystoryapplication.presentation.requestui.PostStoryRequestUiModel
import com.miniapp.mystoryapplication.presentation.utils.reduceFileImage
import com.miniapp.mystoryapplication.presentation.utils.rotateBitmap
import com.miniapp.mystoryapplication.presentation.utils.showToast
import com.miniapp.mystoryapplication.presentation.utils.uriToFile
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewStoryFragment : BaseFragment(R.layout.fragment_new_story) {

    private val binding: FragmentNewStoryBinding by viewBinding(FragmentNewStoryBinding::bind)
    private val vm: NewStoryViewModel by viewModel()
    private val navArgs: NewStoryFragmentArgs by navArgs()
    private val photoFile by lazy { navArgs.file }
    private val isBackCamera by lazy { navArgs.isBackCamera }

    private var resultFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        if (photoFile != null) {
            val imageResult = rotateBitmap(
                BitmapFactory.decodeFile(photoFile?.path ?: ""),
                isBackCamera
            )
            resultFile = photoFile
            binding.ivImageResult.setImageBitmap(imageResult)
        }

        binding.btnGallery.setOnClickListener {
            showToast(getString(R.string.new_story_direct_to_gallery))
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_new_story_fragment_to_cameraXFragment)
        }

        binding.btnUpload.setOnClickListener {
            validatePostStory()
        }
    }

    private fun setupObserver() {
        vm.postStoryResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    findNavController().navigate(R.id.to_stories_list_fragment_new)
                    showToast(getString(R.string.new_story_success_message))
                }
                is ResultState.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    if (it.errorCode == ERROR_CODE_BAD_REQUEST) showDialog(
                        title = resources.getString(R.string.new_story_failed_title),
                        message = resources.getString(R.string.new_story_failed_desc),
                        positiveLabel = "OK"
                    )
                    else showSnackBar(
                        binding.root, it.message ?: "", duration = Snackbar.LENGTH_LONG
                    )
                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri: Uri = result.data?.data as Uri
            resultFile = imageUri.uriToFile(requireContext())
            val bitmapImage = rotateBitmap(
                BitmapFactory.decodeFile((resultFile as File).path),
                true
            )

            binding.ivImageResult.setImageBitmap(bitmapImage)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = FILE_TYPE_IMAGE
        val chooser = Intent.createChooser(intent, getString(R.string.new_story_select_image))
        launcherIntentGallery.launch(chooser)
    }

    private fun validatePostStory() {
        if (resultFile == null || binding.etDescriptionNotes.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.new_story_failed_desc))
        } else postStory()
    }

    private fun postStory() {
        if (resultFile != null) {
            val reduceFile = (resultFile as File).reduceFileImage(isBackCamera)
            val imgRequestBody = reduceFile.asRequestBody(FILE_TYPE_JPEG.toMediaTypeOrNull())
            val imgFile = MultipartBody.Part.createFormData(
                name = "photo",
                filename = resultFile?.name,
                body = imgRequestBody
            )

            val description = binding.etDescriptionNotes.text.toString().trim()
                .toRequestBody(FILE_TYPE_TEXT.toMediaType())

            vm.postStory(
                PostStoryRequestUiModel(
                    imgFile = imgFile,
                    description = description
                )
            )
        }
    }

    companion object {
        private const val ERROR_CODE_BAD_REQUEST = 400

        private const val FILE_TYPE_IMAGE = "image/*"
        private const val FILE_TYPE_JPEG = "image/jpeg"
        private const val FILE_TYPE_TEXT = "text/plain"
    }
}
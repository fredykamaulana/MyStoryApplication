package com.miniapp.mystoryapplication.presentation.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.R
import java.io.File

fun Fragment.requestSinglePermission(
    requestPermissionCode: String,
    isGranted: (Boolean) -> Unit = {}
) =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted(it)
    }.launch(requestPermissionCode)

fun allPermissionsGranted(context: Context, permissionList: Array<String>) =
    permissionList.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

fun Fragment.createFile(): File {
    val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
}

fun Fragment.showToast(message: String) {
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.showLoadStatusSnackBar(
    message: String,
    isLoading: Boolean,
    event: ()-> Unit = {}
) {
    val customView = layoutInflater.inflate(R.layout.layout_custom_snackbar, null)
    Snackbar.make(requireActivity(), requireView(), "", Snackbar.LENGTH_SHORT).apply {
        view.setBackgroundColor(Color.TRANSPARENT)
        view.setPadding(0, 0, 0, 0)

        (view as Snackbar.SnackbarLayout).addView(customView, 0)

        val tv = customView.findViewById<AppCompatTextView>(R.id.tvTextContent)
        val pb = customView.findViewById<CircularProgressIndicator>(R.id.pbCircleLoading)

        tv.text = message
        tv.setOnClickListener {
            event()
        }

        pb.visibility = if (isLoading) View.VISIBLE else View.GONE

    }.show()
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
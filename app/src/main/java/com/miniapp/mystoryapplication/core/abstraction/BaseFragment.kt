package com.miniapp.mystoryapplication.core.abstraction

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.core.di.injectKoinModules

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onAttach(context: Context) {
        injectKoinModules()
        super.onAttach(context)
    }

    fun showSnackBar(
        view: View,
        message: String,
        action: String = "RETRY",
        duration: Int = Snackbar.LENGTH_SHORT,
        actionClick: () -> Unit = {},
    ) {
        Snackbar.make(view, message, duration)
            .setAction(action) { actionClick() }
            .show()
    }

    fun showDialog(
        title: String = "",
        message: String = "",
        positiveLabel: String = "OK",
        positiveAction: () -> Unit = {},
        negativeLabel: String? = null,
        negativeAction: () -> Unit = {}
    ){
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(positiveLabel) { d, _ ->
            positiveAction()
            d.dismiss()
        }
        dialog.setNegativeButton(negativeLabel) { d, _ ->
            negativeAction()
            d.dismiss()
        }
        dialog.show()
    }
}
package com.miniapp.mystoryapplication.presentation.utils

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun View.setAnimatorAlpha(duration: Long = 1000): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, View.ALPHA, 1f).setDuration(duration)
}

fun String.isEmailValid() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { requireActivity().hideKeyboard(it) }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar,
    titleString: String,
    titleTextView: TextView? = null,
    backClickHandler: (() -> Unit)? = null
) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayShowTitleEnabled(titleTextView == null)
    supportActionBar?.title = titleString
    titleTextView?.text = titleString
    toolbar.setNavigationOnClickListener {
        backClickHandler?.run { this() } ?: onBackPressed()
    }
}
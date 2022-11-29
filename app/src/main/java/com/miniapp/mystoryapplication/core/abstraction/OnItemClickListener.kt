package com.miniapp.mystoryapplication.core.abstraction

import android.os.Parcelable
import android.view.View

interface OnItemClickListener {
    fun onClick(item: Parcelable, view: View) {}
}
@file:Suppress("unused")

package com.miniapp.mystoryapplication.core.abstraction

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.miniapp.mystoryapplication.core.di.injectKoinModules

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        injectKoinModules()
    }

}
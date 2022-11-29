package com.miniapp.mystoryapplication.core.delegates

import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** Activity binding delegate, may be used since onCreate up to onDestroy (inclusive) */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

/** Fragment binding delegate, may be used since onViewCreated up to onDestroyView (inclusive) */
fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null
        private var viewLifecycleOwner: LifecycleOwner? = null
        private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

        init {
            this@viewBinding.viewLifecycleOwnerLiveData.observe(this@viewBinding) {
                viewLifecycleOwner?.lifecycle?.removeObserver(this)
                viewLifecycleOwner = it.also { it.lifecycle.addObserver(this) }
            }
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(requireView()).also {
                /** if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it */
                if (viewLifecycleOwner?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.INITIALIZED) == true) {
                    viewLifecycleOwner?.lifecycle?.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            mainHandler.post {
                binding = null
            }
        }
    }

/** Binding delegate for DialogFragments implementing onCreateDialog (like Activities, they don't
 *  have a separate view lifecycle), may be used since onCreateDialog up to onDestroy (inclusive) */
inline fun <T : ViewBinding> DialogFragment.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

inline fun <T : ViewBinding> BottomSheetDialogFragment.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

/** Not really a delegate, just a small helper for RecyclerView.ViewHolders */
inline fun <T : ViewBinding> ViewGroup.viewBinding(factory: (LayoutInflater, ViewGroup, Boolean) -> T) =
    factory(LayoutInflater.from(context), this, false)
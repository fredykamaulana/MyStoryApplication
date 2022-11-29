package com.miniapp.mystoryapplication.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.databinding.LayoutCustomInputTextBinding

class PasswordInputText : ConstraintLayout {
    private var binding: LayoutCustomInputTextBinding? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val view = View.inflate(context, R.layout.layout_custom_input_text, this)
        binding = LayoutCustomInputTextBinding.bind(view)

        setupView()
        setupFocusChangeListener()
        setupTextChangeListener()
        invalidate()
    }

    private fun setupView() {
        binding?.etInputText?.hint = HINT
        binding?.etInputText?.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding?.tilInputText?.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        binding?.tilInputText?.isExpandedHintEnabled = true
        binding?.tilInputText?.labelFor = binding?.etInputText?.id ?: 0
    }

    private fun setupFocusChangeListener() {
        binding?.etInputText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (v as TextInputEditText).hint = ""
                binding?.tilInputText?.hint = HINT
                invalidate()
            } else {
                invalidate()
            }
        }
    }

    private fun setupTextChangeListener() {
        binding?.etInputText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().trim().isEmpty()) {
                binding?.tilInputText?.helperText = ""
            } else {
                binding?.tilInputText?.helperText = if (text.toString().trim().length < 6) {
                    context.getString(R.string.password_input_text_warning_password)
                } else ""
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    fun isInputValid(): Boolean {
        return binding?.etInputText?.text?.length!! >= 6 && binding?.tilInputText?.helperText == null
    }

    fun getText() = binding?.etInputText?.text.toString()

    companion object {
        const val HINT = "Password"
    }
}
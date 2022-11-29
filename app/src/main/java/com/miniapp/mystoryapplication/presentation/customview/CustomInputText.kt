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

class CustomInputText : ConstraintLayout {
    private var binding: LayoutCustomInputTextBinding? = null
    private var hint = ""

    private var etInputText: TextInputEditText? = null
    private var tilInputText: TextInputLayout? = null

    private var onTextChangeListener: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit =
        { _: CharSequence?, _: Int, _: Int, _: Int ->

        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomInputText, 0, 0
        ).apply {
            try {
                hint = getString(R.styleable.CustomInputText_android_hint) ?: ""
            } finally {
                recycle()
            }
        }

        val view = View.inflate(context, R.layout.layout_custom_input_text, this)
        binding = LayoutCustomInputTextBinding.bind(view)
        etInputText = binding?.etInputText
        tilInputText = binding?.tilInputText

        setupView()
        setupFocusChangeListener()
        setupTextChangeListener()
        invalidate()
    }

    private fun setupView() {
        binding?.etInputText?.inputType = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        binding?.etInputText?.hint = hint
        binding?.tilInputText?.endIconMode = TextInputLayout.END_ICON_NONE
        binding?.tilInputText?.isExpandedHintEnabled = true
        binding?.tilInputText?.labelFor = binding?.etInputText?.id ?: 0
    }

    private fun setupFocusChangeListener() {
        binding?.etInputText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (v as TextInputEditText).hint = ""
                binding?.tilInputText?.hint = hint
                invalidate()
            } else {
                invalidate()
            }
        }
    }

    private fun setupTextChangeListener() {
        binding?.etInputText?.doOnTextChanged { text, start, before, count ->
            onTextChangeListener(text, start, before, count)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
        etInputText = null
        tilInputText = null
    }

    fun setOnTextChangeListener(listener: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
        this.onTextChangeListener = listener
    }

    fun setErrorLabel(message: String?) {
        binding?.tilInputText?.helperText = message
    }

    fun isInputValid(): Boolean {
        return binding?.etInputText?.text?.isNotEmpty() == true && binding?.tilInputText?.helperText == null
    }

    fun getText() = binding?.etInputText?.text.toString()
}
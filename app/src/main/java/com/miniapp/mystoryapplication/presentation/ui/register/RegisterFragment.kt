package com.miniapp.mystoryapplication.presentation.ui.register

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.databinding.FragmentRegisterBinding
import com.miniapp.mystoryapplication.presentation.requestui.RegisterRequestUiModel
import com.miniapp.mystoryapplication.presentation.utils.hideKeyboard
import com.miniapp.mystoryapplication.presentation.utils.isEmailValid
import com.miniapp.mystoryapplication.presentation.utils.setAnimatorAlpha
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val vm: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        setupView()
        setupAnimation()
    }

    private fun setupObserver() {
        vm.registerResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    if (it.data?.error == false) {
                        findNavController().navigate(
                            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                                .setIsRegisterSuccess(true)
                        )
                    }
                }
                is ResultState.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    if (it.errorCode == ERROR_CODE_BAD_REQUEST) showDialog(
                        title = resources.getString(R.string.register_status_failed_title),
                        message = resources.getString(R.string.register_status_failed_desc),
                        positiveLabel = "OK"
                    )
                    else showSnackBar(
                        binding.root, it.message ?: "", duration = Snackbar.LENGTH_LONG
                    )
                }
            }
        }
    }

    private fun setupView() {
        binding.containerButtonRegister.apply {
            root.alpha = 0f
            tvButtonText.text = getString(R.string.register_button_label)
            cardProgressButton.setOnClickListener {
                hideKeyboard()
                if (isInputAreValid()) vm.register(collectAllData())
                else showSnackBar(
                    binding.root, resources.getString(R.string.register_input_invalid_message), "OK"
                )
            }
        }
        binding.registerInputName.apply {
            setOnTextChangeListener { text, _, _, _ ->
                if (text.toString().length < 3) {
                    setErrorLabel(resources.getString(R.string.register_input_name_error_message))
                    binding.containerButtonRegister.cardProgressButton.isEnabled = false
                } else {
                    setErrorLabel(null)
                    binding.containerButtonRegister.cardProgressButton.isEnabled = true
                }
            }
        }
        binding.registerInputEmail.apply {
            setOnTextChangeListener { text, _, _, _ ->
                if (!text.toString().isEmailValid()) {
                    setErrorLabel(resources.getString(R.string.register_input_email_error_message))
                    binding.containerButtonRegister.cardProgressButton.isEnabled = false
                } else {
                    setErrorLabel(null)
                    binding.containerButtonRegister.cardProgressButton.isEnabled = true
                }
            }
        }
    }

    private fun isInputAreValid(): Boolean {
        return binding.registerInputName.isInputValid() && binding.registerInputEmail.isInputValid()
                && binding.registerInputPassword.isInputValid()
    }

    private fun collectAllData(): RegisterRequestUiModel {
        return RegisterRequestUiModel(
            name = binding.registerInputName.getText(),
            email = binding.registerInputEmail.getText(),
            password = binding.registerInputPassword.getText()
        )
    }

    private fun setupAnimation() {
        val animateWelcomeSection = AnimatorSet().apply {
            playTogether(
                binding.tvRegisterWelcomeLabel.setAnimatorAlpha(),
                binding.tvRegisterWelcomeDesc.setAnimatorAlpha()
            )
        }

        val animateRegisterSection = AnimatorSet().apply {
            playTogether(
                binding.registerInputName.setAnimatorAlpha(),
                binding.registerInputEmail.setAnimatorAlpha(),
                binding.registerInputPassword.setAnimatorAlpha(),
                binding.containerButtonRegister.root.setAnimatorAlpha()
            )
        }

        AnimatorSet().apply {
            playSequentially(animateWelcomeSection, animateRegisterSection)
            start()
        }
    }

    companion object {
        private const val ERROR_CODE_BAD_REQUEST = 400
    }
}
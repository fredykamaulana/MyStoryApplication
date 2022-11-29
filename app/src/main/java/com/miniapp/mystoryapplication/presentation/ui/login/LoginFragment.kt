package com.miniapp.mystoryapplication.presentation.ui.login

import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.databinding.FragmentLoginBinding
import com.miniapp.mystoryapplication.presentation.requestui.LoginRequestUiModel
import com.miniapp.mystoryapplication.presentation.utils.hideKeyboard
import com.miniapp.mystoryapplication.presentation.utils.isEmailValid
import com.miniapp.mystoryapplication.presentation.utils.setAnimatorAlpha
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val args: LoginFragmentArgs by navArgs()
    private val vm: LoginViewModel by viewModel()

    private var isRegisterSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArguments()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        setupView()
        setupAnimation()
    }

    private fun setupArguments() {
        isRegisterSuccess = args.isRegisterSuccess
    }

    private fun setupObservers() {
        vm.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    vm.saveUserId(it.data?.loginResult?.userId ?: "")
                    vm.saveUserName(it.data?.loginResult?.name ?: "")
                    vm.saveToken(it.data?.loginResult?.token ?: "")

                    findNavController().navigate(R.id.action_login_fragment_to_stories_list_fragment)
                }
                is ResultState.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    if (it.errorCode == ERROR_CODE_UNAUTHORIZED) showDialog(
                        title = resources.getString(R.string.login_status_failed_title),
                        message = resources.getString(R.string.login_status_failed_desc),
                        positiveLabel = "OK"
                    )
                    else showSnackBar(
                        binding.root,
                        it.message ?: "",
                        duration = Snackbar.LENGTH_LONG
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        binding.avLoginLoading.playAnimation()
        binding.containerButtonLogin.apply {
            root.alpha = 0f
            tvButtonText.text = getString(R.string.login_button_label)
            cardProgressButton.setOnClickListener {
                hideKeyboard()
                if (isInputAreValid()) vm.login(collectAllData())
                else showSnackBar(
                    binding.root, resources.getString(R.string.register_input_invalid_message), "OK"
                )
            }
        }
        binding.loginInputUsername.apply {
            setOnTextChangeListener { text, _, _, _ ->
                if (!text.toString().isEmailValid()) {
                    setErrorLabel(resources.getString(R.string.register_input_email_error_message))
                    binding.containerButtonLogin.cardProgressButton.isEnabled = false
                } else {
                    setErrorLabel(null)
                    binding.containerButtonLogin.cardProgressButton.isEnabled = true
                }
            }
        }
        binding.tvRegisterLabel.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
        }

        if (isRegisterSuccess) showDialog(
            title = resources.getString(R.string.register_status_success_title),
            message = resources.getString(R.string.register_status_success_desc)
        )
    }

    private fun isInputAreValid(): Boolean {
        return binding.loginInputUsername.isInputValid() && binding.loginInputPassword.isInputValid()
    }

    private fun collectAllData(): LoginRequestUiModel {
        return LoginRequestUiModel(
            username = binding.loginInputUsername.getText(),
            password = binding.loginInputPassword.getText()
        )
    }

    private fun setupAnimation() {
        val animateAppTextSection = AnimatorSet().apply {
            playTogether(
                binding.tvLabelAppName.setAnimatorAlpha(),
                binding.tvLabelAppDesc.setAnimatorAlpha()
            )
        }

        val animateLoginFieldSection = AnimatorSet().apply {
            playTogether(
                binding.loginInputUsername.setAnimatorAlpha(),
                binding.loginInputPassword.setAnimatorAlpha(),
                binding.containerButtonLogin.root.setAnimatorAlpha()
            )
        }

        val animateRegisterSection = AnimatorSet().apply {
            playTogether(
                binding.tvRegisterDesc.setAnimatorAlpha(),
                binding.tvRegisterLabel.setAnimatorAlpha()
            )
        }

        AnimatorSet().apply {
            playSequentially(
                animateAppTextSection,
                animateLoginFieldSection,
                animateRegisterSection
            )
            start()
        }
    }

    companion object {
        const val ERROR_CODE_UNAUTHORIZED = 401
    }
}
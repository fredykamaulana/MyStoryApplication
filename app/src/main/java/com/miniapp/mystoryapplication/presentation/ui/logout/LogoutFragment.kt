package com.miniapp.mystoryapplication.presentation.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.databinding.FragmentLogoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoutFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLogoutBinding
    private val vm: LogoutViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog_TopRounded)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.btnCancelLogout.setOnClickListener {
            dismiss()
        }
        binding.btnConfirmLogout.setOnClickListener {
            vm.logout()
            dismiss()
            findNavController().navigate(R.id.to_login_fragment)
        }
    }
}
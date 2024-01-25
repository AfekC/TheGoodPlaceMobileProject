package com.example.thegoodplaceapp.screens.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        viewModel = ViewModelProvider(this,
            RegisterViewModelFactory((activity as MainActivity))
        )
            .get(RegisterViewModel::class.java)

        binding.lifecycleOwner = this
        binding.registerViewModel = viewModel

        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        })

        return binding.root
    }
}

package com.example.thegoodplaceapp.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.widget.ImageButton
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this,
            LoginViewModelFactory((activity as MainActivity))
        )
            .get(LoginViewModel::class.java)

        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel

        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        })

        return binding.root
    }
}

package com.example.thegoodplaceapp.screens.guestLandingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.databinding.FragmentGuestLandingPageBinding
import com.google.firebase.auth.FirebaseAuth

class GuestLandingPageFragment : Fragment() {
    private lateinit var binding: FragmentGuestLandingPageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guest_landing_page, container, false)

        binding.lifecycleOwner = this


        binding.root.findViewById<Button>(R.id.login).setOnClickListener {
            Navigation.findNavController(it).navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToLoginFragment())
        }
        binding.root.findViewById<Button>(R.id.register).setOnClickListener {
            Navigation.findNavController(it).navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToRegisterFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {
            Navigation.findNavController(binding.root)
                .navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToHomeFragment())
        }
    }
}
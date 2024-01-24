package com.example.thegoodplaceapp.screens.GuestLandingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R

class GuestLandingPageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_guest_landing_page, container, false)

        (activity as MainActivity).profile.observe(viewLifecycleOwner) { profile ->
            if ((activity as MainActivity).profile.value != null) {
                Navigation.findNavController(view)
                    .navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToHomeFragment())
            }
        }

        view.findViewById<Button>(R.id.login).setOnClickListener {
            Navigation.findNavController(it).navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToLoginFragment())
        }
        view.findViewById<Button>(R.id.register).setOnClickListener {
            Navigation.findNavController(it).navigate(GuestLandingPageFragmentDirections.actionGuestLandingPageFragmentToRegisterFragment())
        }

        return view
    }
}
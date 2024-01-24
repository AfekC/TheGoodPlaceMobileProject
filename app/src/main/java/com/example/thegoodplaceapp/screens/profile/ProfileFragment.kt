package com.example.thegoodplaceapp.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.screens.GuestLandingPage.GuestLandingPageFragmentDirections

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        view.findViewById<TextView>(R.id.logout).setOnClickListener {
            (activity as MainActivity).profile.value = null
            Navigation.findNavController(it).navigate(ProfileFragmentDirections.actionProfileFragmentToGuestLandingPageFragment())
        }

        return view
    }

}

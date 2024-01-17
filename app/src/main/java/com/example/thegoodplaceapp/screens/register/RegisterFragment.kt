package com.example.thegoodplaceapp.screens.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.R

class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

        view.findViewById<Button>(R.id.register).setOnClickListener {
            Navigation.findNavController(it).navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }

        return view
    }
}

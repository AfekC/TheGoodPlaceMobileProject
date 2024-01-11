package com.example.thegoodplaceapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<TextView>(R.id.findPlaces).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_allPlacesListFragment)
        }
        view.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_profileFragment)
        }

        view.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addLocationFragment)
        }
        view.findViewById<ImageButton>(R.id.myCollectionButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_myCollectionFragment)
        }

        val locationText: TextView = view.findViewById(R.id.locationText)

//        runBlocking {
//            locationText.text = "אתה נמצא בעיר: " + GpsLocation.getCityName()
//        }

        return view
    }
}

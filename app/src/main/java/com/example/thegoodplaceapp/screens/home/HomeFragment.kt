package com.example.thegoodplaceapp.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.screens.allLocationsList.AllLocationsListViewModel
import com.example.thegoodplaceapp.screens.allLocationsList.AllLocationsListViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this, HomeViewModelFactory(application))
            .get(HomeViewModel::class.java)

        view.findViewById<TextView>(R.id.findPlaces).setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAllPlacesListFragment())
        }
        view.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }

        view.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAddLocationFragment())
        }
        view.findViewById<ImageButton>(R.id.myCollectionButton).setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToMyCollectionFragment())
        }

        var locationText: TextView = view.findViewById(R.id.locationText)
        viewModel.location.observe(viewLifecycleOwner, Observer { location ->
            locationText.text = "אתה נמצא בעיר: ${location}"
        })

        return view
    }
}

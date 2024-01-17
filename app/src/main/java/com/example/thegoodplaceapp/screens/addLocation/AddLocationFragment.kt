package com.example.thegoodplaceapp.screens.addLocation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.databinding.FragmentAddLocationBinding

class AddLocationFragment : Fragment() {
    private lateinit var viewModel: AddLocationViewModel
    private lateinit var binding: FragmentAddLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_location, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this,
            AddLocationViewModelFactory(LocationDatabase.getInstance(application).locationDao,
                application))
            .get(AddLocationViewModel::class.java)

        binding.lifecycleOwner = this
        binding.addLocationViewModel = viewModel

        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigateUp()
        })

        return binding.root
    }
}

package com.example.thegoodplaceapp.screens.editLocation

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.room.InvalidationTracker
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.databinding.FragmentEditLocationBinding


class EditLocationFragment : Fragment() {
    private lateinit var viewModel: EditLocationViewModel
    private lateinit var binding: FragmentEditLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_location, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this,
            EditLocationViewModelFactory(
                LocationDatabase.getInstance(application).locationDao,
                application)
        )
            .get(EditLocationViewModel::class.java)

        binding.lifecycleOwner = this
        binding.locationViewModel = viewModel

        binding.exitButton.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigateUp()
        })

        var args =EditLocationFragmentArgs.fromBundle(arguments!!)

        viewModel.location.value = args.location

        return binding.root
    }

}

package com.example.thegoodplaceapp.screens.allLocationsList

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.DynamicRow
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.databinding.FragmentAllLocationsListBinding


class AllLocationsListFragment : Fragment() {
    private lateinit var viewModel: AllLocationsListViewModel
    private lateinit var binding: FragmentAllLocationsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_locations_list, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this,
            AllLocationsListViewModelFactory(
                LocationDatabase.getInstance(application).locationDao,
                application)
        )
            .get(AllLocationsListViewModel::class.java)


        binding.root.findViewById<ImageButton>(R.id.myCollectionButton).setOnClickListener {
            Navigation.findNavController(it).navigate(AllLocationsListFragmentDirections.actionAllPlacesListFragmentToMyCollectionFragment())
        }
        binding.root.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(AllLocationsListFragmentDirections.actionAllPlacesListFragmentToProfileFragment())
        }
        binding.root.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(AllLocationsListFragmentDirections.actionAllPlacesListFragmentToAddLocationFragment())
        }

        val itemTable: TableLayout = binding.root.findViewById(R.id.itemsTable)

        displayData(container!!.context, itemTable)

        return binding.root
    }

    private fun displayData(context: Context, itemTable: TableLayout) {
        viewModel.locations.observe(viewLifecycleOwner) { locations ->
            itemTable.removeAllViews()
            locations.forEach { location ->
                itemTable.addView(
                    DynamicRow.getRow(
                        context,
                        false,
                        location
                    )
                )
            }
        }
    }
}

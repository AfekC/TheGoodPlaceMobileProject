package com.example.thegoodplaceapp.screens.myCollection

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.DynamicRow
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.screens.allLocationsList.AllLocationsListViewModel
import com.example.thegoodplaceapp.screens.allLocationsList.AllLocationsListViewModelFactory

class MyCollectionFragment : Fragment() {
    private lateinit var viewModel: MyCollectionListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_my_collection, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this,
            MyCollectionViewModelFactory(
                LocationDatabase.getInstance(application).locationDao,
                application)
        )
            .get(MyCollectionListViewModel::class.java)

        view.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        view.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(MyCollectionFragmentDirections.actionMyCollectionFragmentToProfileFragment())
        }
        view.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(MyCollectionFragmentDirections.actionMyCollectionFragmentToAddLocationFragment())
        }

        val itemTable: TableLayout = view.findViewById(R.id.itemsTable)


        displayData(container!!.context, itemTable)

        return view
    }

    private fun displayData(context: Context, itemTable: TableLayout) {
        viewModel.locations.observe(viewLifecycleOwner) { locations ->
            itemTable.removeAllViews()
            locations.forEach { location ->
                location.image = R.drawable.avatar_default_icon
                itemTable.addView(
                    DynamicRow.getRow(
                        context,
                        true,
                        location
                    )
                )
            }
        }
    }
}

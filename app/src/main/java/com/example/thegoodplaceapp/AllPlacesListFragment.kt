package com.example.thegoodplaceapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import androidx.navigation.Navigation


class AllPlacesListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_all_places_list, container, false)

        view.findViewById<ImageButton>(R.id.myCollectionButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_allPlacesListFragment_to_myCollectionFragment)
        }
        view.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_allPlacesListFragment_to_profileFragment)
        }
        view.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_allPlacesListFragment_to_addLocationFragment)
        }

        val itemTable: TableLayout = view.findViewById(R.id.itemsTable)

        createData(container!!.context, itemTable)

        return view
    }

    private fun createData(context: Context, itemTable: TableLayout) {
        for (i in 1..20) {
            itemTable.addView(DynamicRow.getRow(context, false,"", R.drawable.avatar_default_icon, i.toString(), i.toString(), i.toString()))
        }
    }
}

package com.example.thegoodplaceapp

import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TextView
import androidx.navigation.Navigation

class MyCollectionFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_my_collection, container, false)


        view.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        view.findViewById<ImageButton>(R.id.myProfileButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_myCollectionFragment_to_profileFragment)
        }
        view.findViewById<ImageButton>(R.id.addPlaceButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_myCollectionFragment_to_addLocationFragment)
        }

        val itemTable: TableLayout = view.findViewById(R.id.itemsTable)


        createData(container!!.context, itemTable)

        return view
    }

    private fun createData(context: Context, itemTable: TableLayout) {
        for (i in 1..20) {
            Log.i("", i.toString())
            itemTable.addView(DynamicRow.getRow(context, true,i.toString(), R.drawable.avatar_default_icon, i.toString(), i.toString(), i.toString()))
        }
    }
}

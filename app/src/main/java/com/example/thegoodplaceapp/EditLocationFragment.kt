package com.example.thegoodplaceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText


class EditLocationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_edit_location, container, false)

        view.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        val image: ImageView = view.findViewById(R.id.locationImage)
        val name: TextInputEditText = view.findViewById(R.id.nameInput)
        val city: TextInputEditText = view.findViewById(R.id.cityInput)
        val description: TextInputEditText = view.findViewById(R.id.descriptionInput)

        var args = EditLocationFragmentArgs.fromBundle(arguments!!)

        image.setImageResource(args.image)
        name.setText(args.name)
        city.setText(args.city)
        description.setText(args.description)

        return view
    }

}

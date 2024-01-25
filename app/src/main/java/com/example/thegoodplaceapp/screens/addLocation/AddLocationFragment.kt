package com.example.thegoodplaceapp.screens.addLocation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.databinding.FragmentAddLocationBinding
import com.example.thegoodplaceapp.utils.ImageUtil
import java.io.ByteArrayOutputStream
import java.io.File

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
                application,
                (activity as MainActivity)))
            .get(AddLocationViewModel::class.java)

        binding.lifecycleOwner = this
        binding.addLocationViewModel = viewModel


        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigateUp()
        })

        val image: ImageView = binding.root.findViewById<ImageButton>(R.id.locationImage)


        (activity as MainActivity).uriResult.observe(viewLifecycleOwner) { uri ->
            Log.i("AFEK", uri.toString())
            if (uri != null) {
                image.setImageURI(uri)
                val byteArray = ImageUtil.imageToByteArray(image)
                viewModel.image.value = byteArray
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                image.setImageBitmap(bitmap)
                (activity as MainActivity).uriResult.value = null
            }
        }



        return binding.root
    }
}

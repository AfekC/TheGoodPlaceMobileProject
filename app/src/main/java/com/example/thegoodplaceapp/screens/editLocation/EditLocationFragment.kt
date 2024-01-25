package com.example.thegoodplaceapp.screens.editLocation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.room.InvalidationTracker
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.databinding.FragmentEditLocationBinding
import com.example.thegoodplaceapp.utils.ImageUtil
import java.io.ByteArrayOutputStream


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
                application,
                (activity as MainActivity)))
            .get(EditLocationViewModel::class.java)

        var args =EditLocationFragmentArgs.fromBundle(arguments!!)

        viewModel.location.value = args.location

        binding.lifecycleOwner = this
        binding.locationViewModel = viewModel

        binding.exitButton.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigateUp()
        })

        val imageView: ImageView = binding.root.findViewById<ImageButton>(R.id.locationImage)

        ImageUtil.loadImage(imageView, viewModel.location.value!!.image)

        (activity as MainActivity).uriResult.observe(viewLifecycleOwner) { uri ->
            Log.i("AFEK", uri.toString())
            if (uri != null) {
                imageView.setImageURI(uri)
                val byteArray = ImageUtil.imageToByteArray(imageView)
                viewModel.location.value!!.image = byteArray
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                imageView.setImageBitmap(bitmap)
                (activity as MainActivity).uriResult.value = null
            }
        }

        return binding.root
    }
}

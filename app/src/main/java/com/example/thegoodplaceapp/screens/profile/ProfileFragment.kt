package com.example.thegoodplaceapp.screens.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.R
import com.example.thegoodplaceapp.databinding.FragmentProfileBinding
import com.example.thegoodplaceapp.screens.register.RegisterFragmentDirections
import com.example.thegoodplaceapp.utils.ImageUtil

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        viewModel = ViewModelProvider(this,
            ProfileViewModelFactory((activity as MainActivity))
        )
            .get(ProfileViewModel::class.java)

        binding.lifecycleOwner = this
        binding.profileViewModel = viewModel

        binding.root.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        val imageView: ImageView = binding.root.findViewById<ImageButton>(R.id.locationImage)

        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { isSaved ->
            Navigation.findNavController(binding.root).navigateUp()
        })
        viewModel.logout.observe(viewLifecycleOwner, Observer { islogedOut ->
            Navigation.findNavController(binding.root).navigate(ProfileFragmentDirections.actionProfileFragmentToGuestLandingPageFragment())
        })
        viewModel.image.observe(viewLifecycleOwner, Observer { byteArray ->
            ImageUtil.loadImage(imageView, byteArray)
       })

        val byteArray: ByteArray? = null
        ImageUtil.loadImage(imageView, byteArray)

        (activity as MainActivity).uriResult.observe(viewLifecycleOwner) { uri ->
            Log.i("AFEK", uri.toString())
            if (uri != null) {
                imageView.setImageURI(uri)
                val byteArray = ImageUtil.imageToByteArray(imageView)
                viewModel.image.value = byteArray
                (activity as MainActivity).uriResult.value = null
            }
        }

        viewModel.initImage()

        return binding.root
    }

}

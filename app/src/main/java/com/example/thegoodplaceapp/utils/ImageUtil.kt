package com.example.thegoodplaceapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.example.thegoodplaceapp.MainActivity
import java.io.ByteArrayOutputStream

object ImageUtil {
    fun loadImage(imageView: ImageView, byteArray: ByteArray?) {
        if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            imageView.setImageBitmap(bitmap)
        }
    }

    fun imageToByteArray(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }
}

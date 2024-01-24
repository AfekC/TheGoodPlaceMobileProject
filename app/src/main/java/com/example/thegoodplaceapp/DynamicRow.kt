package com.example.thegoodplaceapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.navigation.Navigation
import com.example.thegoodplaceapp.model.Location
import com.example.thegoodplaceapp.screens.myCollection.MyCollectionFragmentDirections

class DynamicRow {

    companion object {
        @JvmStatic
        fun getRow(
            it: Context,
            isEditable: Boolean,
            location: Location,
        ): TableRow {
            val tr: TableRow = TableRow(it)
            tr.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250)
            tr.setPadding(4)

            val rowLayout: LinearLayout = getRowLayout(it)
            val textLayout: LinearLayout = getTextLayout(it)
            val image: ImageView = getImageToRow(it, location.image)

            addLocationData(it, textLayout, location)

            rowLayout.addView(image)
            rowLayout.addView(textLayout)

            if (isEditable) {
                val action = MyCollectionFragmentDirections.actionMyCollectionFragmentToEditLocationFragment(
                    location)
                val editLayout: LinearLayout = getButtonLayout(it, action)

                rowLayout.addView(editLayout)
            }

            tr.addView(rowLayout)
            return tr
        }

        private fun getRowLayout(it: Context): LinearLayout {
            val rowLayout: LinearLayout = LinearLayout(it)
            val layoutParams: TableRow.LayoutParams =
                TableRow.LayoutParams(880, LinearLayout.LayoutParams.MATCH_PARENT)
            rowLayout.orientation = LinearLayout.HORIZONTAL
            rowLayout.layoutParams = layoutParams
            rowLayout.setBackgroundColor(Color(0xFFDDDDDD).hashCode())
            return rowLayout
        }

        private fun getTextLayout(it: Context): LinearLayout {
            val textLayout: LinearLayout = LinearLayout(it)
            textLayout.orientation = LinearLayout.VERTICAL
            textLayout.setPadding(8)
            val textLayoutParams: TableRow.LayoutParams = TableRow.LayoutParams(
                390,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textLayout.layoutParams = textLayoutParams
            return textLayout
        }

        private fun getImageToRow(it: Context, imageByteArray: ByteArray?): ImageView {
            val image: ImageView = ImageView(it)
            val imageParams: TableRow.LayoutParams = TableRow.LayoutParams(250, 250)
            imageParams.setMargins(6)
            imageParams.rightMargin = 50
            image.layoutParams = imageParams
            if (imageByteArray != null) {
                val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                image.setImageBitmap(bitmap)
            } else {
                image.setImageResource(R.drawable.baseline_collections_55)
            }
            return image
        }

        private fun getButtonLayout(it: Context, action: MyCollectionFragmentDirections.ActionMyCollectionFragmentToEditLocationFragment): LinearLayout {
            val layout: LinearLayout = LinearLayout(it)
            layout.layoutParams = TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            val button: ImageButton = ImageButton(it)
            button.setImageResource(R.drawable.baseline_edit_square_24)
            button.setOnClickListener(Navigation.createNavigateOnClickListener(action))

            layout.addView(button)

            layout.gravity = Gravity.END or Gravity.BOTTOM

            return layout
        }

        private fun addLocationData(
            it: Context,
            parentLayout: LinearLayout,
            location: Location
        ) {
            val locationName: TextView = TextView(it)
            locationName.setText("Name: " + location.name)
            locationName.textSize = 20F
            parentLayout.addView(locationName)

            val locationCity: TextView = TextView(it)
            locationCity.setText("City: " + location.city)
            locationName.textSize = 18F

            parentLayout.addView(locationCity)

            val locationDescription: TextView = TextView(it)
            locationDescription.setText("Description: " + location.description)
            locationName.textSize = 18F
            parentLayout.addView(locationDescription)
        }
    }

}

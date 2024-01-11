package com.example.thegoodplaceapp

import android.content.Context
import android.util.Log
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
import androidx.navigation.findNavController

class DynamicRow {

    companion object {
        @JvmStatic
        fun getRow(
            it: Context,
            isEditable: Boolean,
            id: String,
            imageSrc: Int,
            name: String,
            city: String,
            description: String
        ): TableRow {
            val tr: TableRow = TableRow(it)
            tr.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250)
            tr.setPadding(4)

            val rowLayout: LinearLayout = getRowLayout(it)
            val textLayout: LinearLayout = getTextLayout(it)
            val image: ImageView = getImageToRow(it, imageSrc)

            addLocationData(it, textLayout, name, city, description)

            rowLayout.addView(image)
            rowLayout.addView(textLayout)

            if (isEditable) {
                val action = MyCollectionFragmentDirections.actionMyCollectionFragmentToEditLocationFragment(
                        id,
                        imageSrc,
                        name,
                        city,
                        description)
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
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textLayout.layoutParams = textLayoutParams
            return textLayout
        }

        private fun getImageToRow(it: Context, imageSrc: Int): ImageView {
            val image: ImageView = ImageView(it)
            val imageParams: TableRow.LayoutParams = TableRow.LayoutParams(250, 250)
            imageParams.setMargins(6)
            imageParams.rightMargin = 50
            image.layoutParams = imageParams
            image.setImageResource(imageSrc)
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
            name: String,
            city: String,
            description: String
        ) {
            val locationName: TextView = TextView(it)
            locationName.setText("Name: " + name)
            locationName.textSize = 20F
            parentLayout.addView(locationName)

            val locationCity: TextView = TextView(it)
            locationCity.setText("City: " + city)
            locationName.textSize = 18F

            parentLayout.addView(locationCity)

            val locationDescription: TextView = TextView(it)
            locationDescription.setText("Description: " + description)
            locationName.textSize = 18F
            parentLayout.addView(locationDescription)
        }
    }

}

package com.example.thegoodplaceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TableRow.LayoutParams
import android.widget.TextView
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.graphics.Color
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.core.view.setPadding

class AllPlacesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_places_list)

        val myCollectionButton: ImageButton = findViewById(R.id.my_collection_button)
        val profileButton: ImageButton = findViewById(R.id.my_profile_button)
        val itemTable: TableLayout = findViewById(R.id.items_table)
        val addLocationButton: ImageButton = findViewById(R.id.add_place_button)





        itemTable.setPadding(14)

        for (i in 1..20) {
            val tr: TableRow = TableRow(this)
            tr.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250)
            tr.setPadding(4)

            val rowLayout: LinearLayout = LinearLayout(this)
            val layoutParams: LayoutParams = LayoutParams()
            layoutParams.width = 1000
            rowLayout.orientation = LinearLayout.HORIZONTAL
            rowLayout.layoutParams = layoutParams
            rowLayout.setBackgroundColor(Color(0xFFDDDDDD).hashCode())

            val image: ImageView = ImageView(this)
            val imageParams: LayoutParams = LayoutParams(250, 250)
            imageParams.setMargins(6)
            imageParams.rightMargin = 50
            image.layoutParams = imageParams
            image.setImageResource(R.drawable.avatar_default_icon)

            rowLayout.addView(image)

            val textLayout: LinearLayout = LinearLayout(this)
            textLayout.orientation = LinearLayout.VERTICAL
            textLayout.setPadding(8)
            val textLayoutParams: LayoutParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
            textLayout.layoutParams = textLayoutParams



            val locationName: TextView = TextView(this)
            locationName.setText("Name: " + i.toString())
            locationName.textSize = 20F
            textLayout.addView(locationName)

            val locationCity: TextView = TextView(this)
            locationCity.setText("City: " + i.toString())
            locationName.textSize = 18F

            textLayout.addView(locationCity)

            val locationDescription: TextView = TextView(this)
            locationDescription.setText("Description: " + i.toString())
            locationName.textSize = 18F
            textLayout.addView(locationDescription)


            rowLayout.addView(textLayout)


            tr.addView(rowLayout)
            itemTable.addView(tr)
        }

        myCollectionButton.setOnClickListener(::myCollectionClicked)
        profileButton.setOnClickListener(::profileClicked)
        addLocationButton.setOnClickListener {
            startActivity(Intent(this, AddLocation::class.java))
        }
    }

    fun myCollectionClicked(view: View) {
        val intent = Intent(this, AllPlacesListActivity::class.java)
        startActivity(intent)
    }

    fun profileClicked(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}
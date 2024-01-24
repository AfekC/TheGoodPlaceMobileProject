package com.example.thegoodplaceapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "location_table")
@Serializable
class Location (
    @PrimaryKey(autoGenerate = true)
    var locationId: Long = 0L,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "city")
    var city: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "image_data")
    var image: ByteArray? = null,
    @ColumnInfo(name = "creator_id")
    var creator: String = ""
): java.io.Serializable

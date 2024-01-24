package com.example.thegoodplaceapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
class Profile (
    var profileId: Long = 0L,
    var name: String = "",
    var image: ByteArray? = null
): java.io.Serializable

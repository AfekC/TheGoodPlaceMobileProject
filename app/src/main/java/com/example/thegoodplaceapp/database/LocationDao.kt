package com.example.thegoodplaceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocationDao {
    @Insert
    fun insert(location: Location)

    @Update
    fun update(location: Location)

    @Delete
    fun delete(location: Location)

    @Query("DELETE FROM location_table")
    fun deleteAll()

    @Query("SELECT * FROM location_table WHERE locationId = :key")
    fun get(key: Long): Location

    @Query("SELECT * FROM location_table")
    fun getAllLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM location_table WHERE creator_id = :creator")
    fun getMyLocations(creator: String): LiveData<List<Location>>
}
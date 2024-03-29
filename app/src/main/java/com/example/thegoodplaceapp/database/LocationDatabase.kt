package com.example.thegoodplaceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thegoodplaceapp.model.Location

@Database(entities = [Location::class], version = 4, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract val locationDao: LocationDao
    companion object {
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context) : LocationDatabase {
            synchronized (this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocationDatabase::class.java,
                        "location_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
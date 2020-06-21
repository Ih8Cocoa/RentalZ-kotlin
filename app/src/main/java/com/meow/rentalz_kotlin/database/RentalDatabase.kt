package com.meow.rentalz_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class RentalDatabase : RoomDatabase() {
    abstract val propertyDao: PropertyDao

    // singleton pattern
    companion object {
        @Volatile
        private var INSTANCE: RentalDatabase? = null

        fun getDatabase(context: Context): RentalDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null) {
                return tmpInstance;
            }
            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    RentalDatabase::class.java,
                    "property_database"
                ).build()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}
package com.meow.rentalz_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meow.rentalz_kotlin.converters.RoomTypeConverters

@Database(entities = [Property::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
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
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}
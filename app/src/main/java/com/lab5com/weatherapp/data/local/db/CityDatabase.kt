package com.lab5com.weatherapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lab5com.weatherapp.data.local.db.dao.CityDao
import com.lab5com.weatherapp.data.local.db.entity.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: CityDatabase? = null
        fun getDatabase(context: Context): CityDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                //créer une instance de database
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        CityDatabase::class.java,
                        "city_database"
                    )
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
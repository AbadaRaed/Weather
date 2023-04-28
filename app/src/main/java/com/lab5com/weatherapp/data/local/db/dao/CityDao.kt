package com.lab5com.weatherapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lab5com.weatherapp.data.local.db.entity.City

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getAll(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Query("Select COUNT(*) from cities where name=:name")
    suspend fun getCity(name:String): Long
}
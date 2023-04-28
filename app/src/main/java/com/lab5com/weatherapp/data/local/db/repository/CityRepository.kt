package com.lab5com.weatherapp.data.local.db.repository

import androidx.lifecycle.LiveData
import com.lab5com.weatherapp.data.local.db.dao.CityDao
import com.lab5com.weatherapp.data.local.db.entity.City
import javax.inject.Singleton

@Singleton
class CityRepository(private val cityDao: CityDao) {

    fun getAllCities(): LiveData<List<City>> = cityDao.getAll()

    suspend fun insertCity(city: City) {
        cityDao.insert(city)
    }

    suspend fun getCity(name: String): Long {
        return cityDao.getCity(name)
    }
}
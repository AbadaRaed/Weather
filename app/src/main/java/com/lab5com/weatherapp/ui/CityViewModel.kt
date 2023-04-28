package com.lab5com.weatherapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5com.weatherapp.data.local.db.CityDatabase
import com.lab5com.weatherapp.data.local.db.entity.City
import com.lab5com.weatherapp.data.local.db.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(context: Context) : ViewModel() {
    var listOfCities: LiveData<List<City>>
    private var repository: CityRepository

    init {
        val cityDao = CityDatabase.getDatabase(context).cityDao()
        repository = CityRepository(cityDao)
        listOfCities = repository.getAllCities()
    }

    fun insertCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCity(city)
        }
    }

    suspend fun getCity(name: String): Long {
        return repository.getCity(name)

    }

}
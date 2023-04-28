package com.lab5com.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.lab5com.weatherapp.BuildConfig
import com.lab5com.weatherapp.R
import com.lab5com.weatherapp.adapter.CityAdapter
import com.lab5com.weatherapp.api.WeatherAPIClient
import com.lab5com.weatherapp.data.local.db.entity.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var cityViewModel: CityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        // Initialize Adapter
        adapter = CityAdapter()
        recyclerView.adapter = adapter

        adapter.onItemClickListener(object : CityAdapter.onItemClickListener {
            override fun onItemClick(position: Int, cities: List<City>) {
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra("city", cities[position])
                startActivity(intent)
            }
        })

        // Initialize ViewModel
        val factory = CityViewModelFactory(this)
        cityViewModel = ViewModelProvider(this, factory).get(CityViewModel::class.java) // ViewModel
        cityViewModel.listOfCities.observe(this) {
            adapter.setCities(it)
        }

        // Initialize the SDK
        Places.initialize(applicationContext, BuildConfig.GOOGLE_PLACE_API_KEY)
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG))
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.d(TAG, "Place: ${place.name}, latitude: ${place.latLng?.latitude}")
                Log.d(TAG, "Place: ${place.name}, longitude: ${place.latLng?.longitude}")
                val latitude = place.latLng?.latitude
                val longitude = place.latLng?.longitude
                val name = place.name

                CoroutineScope(Dispatchers.IO).launch {
                    val response = WeatherAPIClient.apiInterface.getWeather(
                        latitude,
                        longitude,
                        BuildConfig.WEATHER_API_KEY
                    )

                    val time = getDateTime(response.current.dt)
                    val temp = response.current.temp
                    val icon = response.current.weather[0].icon
                    val description = response.current.weather[0].description
                    val humidity = response.current.humidity
                    val pressure = response.current.pressure
                    val feelsLike = response.current.feelsLike
                    val wind = response.current.windSpeed
                    Log.d(
                        TAG,
                        "City: $name Temperature: $temp °C Icon: $icon Time: $time Description: $description " +
                                "Humidity: $humidity % Pressure: $pressure hPa Wind: $wind km/h fells like: $feelsLike °"
                    )
                    val newCity = City(
                        0,
                        time,
                        name,
                        temp,
                        icon,
                        description,
                        humidity,
                        pressure,
                        wind,
                        feelsLike
                    )
                    withContext(Dispatchers.Main)
                    {
                        if (name?.let { cityViewModel.getCity(it) } == 0L) {
                            cityViewModel.insertCity(newCity)
                        }
                    }
                }
            }

            override fun onError(status: Status) {
                Log.e(TAG, "An error occurred: $status")
                Toast.makeText(
                    this@MainActivity,
                    "Failed to get place '${status.statusMessage}'",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    private fun getDateTime(time: Int): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.FRANCE)
        return simpleDateFormat.format(time * 1000L)

    }

}
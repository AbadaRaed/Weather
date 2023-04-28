package com.lab5com.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lab5com.weatherapp.R
import com.lab5com.weatherapp.data.local.db.entity.City
import com.lab5com.weatherapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)
        setContentView(binding.root)

        val city = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("city", City::class.java)
        } else {
            intent.getParcelableExtra("city")
        }

        if (city != null) {
            binding.tvCityName.text = city.name
            binding.txtState.text = city.description.uppercase()
            binding.pressure.text = city.pressure.toString()
            binding.txtTemperature.text = city.temperature.toString()
            binding.humidity.text = city.humidity.toString()
            binding.txtDate.text = city.time
            binding.wind.text = city.wind.toString()
            binding.txtFeels.text = city.feelsLike.toString()
            val drawableResourceId = when (city.icon) {
                "01d" -> R.drawable.ic_01d
                "01n" -> R.drawable.ic_01n
                "02d" -> R.drawable.ic_02d
                "02n" -> R.drawable.ic_02n
                "03d" -> R.drawable.ic_03d
                "03n" -> R.drawable.ic_03n
                "04d" -> R.drawable.ic_04d
                "04n" -> R.drawable.ic_04n
                "09d" -> R.drawable.ic_09d
                "09n" -> R.drawable.ic_09n
                "010d" -> R.drawable.ic_10d
                "010n" -> R.drawable.ic_10n
                "011d" -> R.drawable.ic_11d
                "011n" -> R.drawable.ic_11n
                "013d" -> R.drawable.ic_13d
                "013n" -> R.drawable.ic_13n
                "050n" -> R.drawable.ic_50d
                else -> R.drawable.ic_50n
            }
            binding.imgDetailState.setImageResource(drawableResourceId)


        }
    }
}
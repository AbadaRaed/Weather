package com.lab5com.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lab5com.weatherapp.R
import com.lab5com.weatherapp.data.local.db.entity.City
import com.lab5com.weatherapp.databinding.ItemRvCityWeatherBinding

class CityAdapter() : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private var cities: List<City> = listOf()
    lateinit var mlistener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int, cities: List<City>)
    }

    fun onItemClickListener(listener: onItemClickListener) {
        mlistener = listener
    }

    inner class CityViewHolder(itemView: ItemRvCityWeatherBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView.root) {
        val cityNameTextView: TextView = itemView.tvCity
        val temTextView: TextView = itemView.tvTemp
        val iconImageView: ImageView = itemView.imgWeather

        init {
            itemView.root.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition, cities)
            }
        }

    }

    fun setCities(newData: List<City>) {
        cities = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        // Obtain an instance of LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        // Inflate the layout for the list item
        val listItemBinding = DataBindingUtil.inflate<ItemRvCityWeatherBinding>(
            layoutInflater,
            R.layout.item_rv_city_weather,
            parent,
            false
        )
        // Return a new ViewHolder with the binding
        return CityViewHolder(listItemBinding, mlistener)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.cityNameTextView.text = city.name
        holder.temTextView.text = city.temperature.toString()
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
        //val drawable = ContextCompat.getDrawable(holder.itemView.context, drawableResourceId)
        holder.iconImageView.setImageResource(drawableResourceId)

    }

    override fun getItemCount(): Int {
        return cities.size
    }
}
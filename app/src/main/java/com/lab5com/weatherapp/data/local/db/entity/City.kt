package com.lab5com.weatherapp.data.local.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "cities")
@Parcelize
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val name: String?,
    val temperature: Int,
    val icon: String,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val wind: Double,
    val feelsLike: Int
) : Parcelable


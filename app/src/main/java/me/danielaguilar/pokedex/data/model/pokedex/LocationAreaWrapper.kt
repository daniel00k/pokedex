package me.danielaguilar.pokedex.data.model.pokedex

import com.google.gson.annotations.SerializedName

data class LocationAreaWrapper(
    @SerializedName("location_area")
    val locationArea: LocationArea
)
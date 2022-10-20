package me.danielaguilar.pokedex.util

import me.danielaguilar.pokedex.BuildConfig

class PokemonPropertiesExtractor {

    fun getIdFromUrl(url: String): Int {
        val lastValue = url.split("/").fold(mutableListOf<String>()) { acc, value ->
            if (value.isNullOrBlank()) {
                acc
            } else {
                acc.add(value)
                acc
            }
        }.last()
        return lastValue.toInt()
    }

    fun getImageUrlFromUrl(url: String): String {
        val id = getIdFromUrl(url)
        return "${BuildConfig.BASE_POKEMON_IMAGE_URL}/${id}.png"
    }
}
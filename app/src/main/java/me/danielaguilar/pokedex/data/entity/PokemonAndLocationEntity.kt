package me.danielaguilar.pokedex.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "locationId"])
class PokemonAndLocationEntity(
    val pokemonId: Int,
    val locationId: Int
)
package me.danielaguilar.pokedex.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "kindId"])
class PokemonAndKindEntity(
    val pokemonId: Int,
    val kindId: Int
)
package me.danielaguilar.pokedex.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "attackId"])
class PokemonAndAttackEntity(
    val pokemonId: Int,
    val attackId: Int
)
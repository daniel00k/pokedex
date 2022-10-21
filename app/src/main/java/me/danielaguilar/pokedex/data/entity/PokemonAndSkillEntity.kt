package me.danielaguilar.pokedex.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "skillId"])
class PokemonAndSkillEntity(
    val pokemonId: Int,
    val skillId: Int
)
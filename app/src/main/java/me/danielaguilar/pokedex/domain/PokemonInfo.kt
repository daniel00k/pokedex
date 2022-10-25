package me.danielaguilar.pokedex.domain

data class PokemonInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val evolution: String? = null,
    val types: List<PokemonKind>,
    val attacks: List<PokemonAttack>,
    val skills: List<PokemonSkill>,
    val locations: List<PokemonLocation>
)

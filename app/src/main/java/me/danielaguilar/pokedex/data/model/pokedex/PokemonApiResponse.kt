package me.danielaguilar.pokedex.data.model.pokedex

data class PokemonApiResponse(
    val abilities: List<AbilityWrapper>,
    val moves: List<MoveWrapper>,
    val types: List<PokemonKindWrapper>
)

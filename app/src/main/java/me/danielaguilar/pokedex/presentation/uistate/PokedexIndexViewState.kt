package me.danielaguilar.pokedex.presentation.uistate

import me.danielaguilar.pokedex.domain.PokemonSummary

sealed class PokedexIndexViewState {
    data class Success(val pokemonList: List<PokemonSummary>) : PokedexIndexViewState()
    data class Error(val pokemonList: List<PokemonSummary>, val errorMessage: String) : PokedexIndexViewState()
    object Loading : PokedexIndexViewState()
}
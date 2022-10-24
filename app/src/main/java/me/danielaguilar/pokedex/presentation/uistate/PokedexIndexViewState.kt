package me.danielaguilar.pokedex.presentation.uistate

sealed class PokedexIndexViewState<out T> {
    data class Success<out T : Any>(val data: T) : PokedexIndexViewState<T>()
    data class Error(val error: Exception? = null, val errorMessage: String) :
        PokedexIndexViewState<Nothing>()

    object Loading : PokedexIndexViewState<Nothing>()
}
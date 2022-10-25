package me.danielaguilar.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.usecase.SearchPokemonUseCase
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val searchPokemonUseCase: SearchPokemonUseCase) :
    ViewModel() {
    lateinit var job: Job
    private val _viewState =
        MutableStateFlow<PokedexIndexViewState<PokemonInfo>>(PokedexIndexViewState.Loading)
    val viewState: StateFlow<PokedexIndexViewState<PokemonInfo>>
        get() = _viewState

    override fun onCleared() {
        super.onCleared()
        if (this::job.isInitialized) {
            job.cancel()
        }
    }

    fun getPokemonInfo(pokemonId: Int) {
        _viewState.value = PokedexIndexViewState.Loading
        job = viewModelScope.launch(Dispatchers.IO) {
            searchPokemonUseCase.getById(pokemonId).collect { pokemon ->
                _viewState.value = PokedexIndexViewState.Success(pokemon)
            }
        }
    }
}
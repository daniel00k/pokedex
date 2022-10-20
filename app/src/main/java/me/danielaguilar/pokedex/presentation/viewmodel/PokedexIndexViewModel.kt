package me.danielaguilar.pokedex.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.danielaguilar.pokedex.domain.PokemonSummary
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.usecase.GetAllPokemonUseCase
import me.danielaguilar.pokedex.util.extension.toDomainPokemonSummary
import javax.inject.Inject

@HiltViewModel
class PokedexIndexViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllPokemonUseCase: GetAllPokemonUseCase
) : ViewModel() {

    private val pokemonRecyclerStateKey = "pokemons"
    private val recyclerStateKey = "recyclerState"
    private val _viewState = MutableLiveData<PokedexIndexViewState>(PokedexIndexViewState.Loading)
    val viewState: LiveData<PokedexIndexViewState>
        get() = _viewState
    private lateinit var job: Job

    fun setData(pokemons: List<PokemonSummary>) {
        savedStateHandle[pokemonRecyclerStateKey] = pokemons
    }

    fun setRecyclerState(state: Parcelable) {
        savedStateHandle[recyclerStateKey] = state
    }

    fun getRecyclerState(): Parcelable? {
        if (savedStateHandle.contains(recyclerStateKey)) {
            return savedStateHandle[recyclerStateKey]
        }
        return null
    }

    fun getPokemons() {
        if (savedStateHandle.contains(pokemonRecyclerStateKey)) {
            _viewState.postValue(PokedexIndexViewState.Success(savedStateHandle[pokemonRecyclerStateKey]!!))
            return
        }
        getPokemonList()
    }

    private fun getPokemonList() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = getAllPokemonUseCase.getAllPokemon()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _viewState.postValue(PokedexIndexViewState.Success(response.body()!!.results.map { p -> p.toDomainPokemonSummary() }))
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        _viewState.postValue(PokedexIndexViewState.Error(emptyList(), message))
    }

    override fun onCleared() {
        super.onCleared()
        if (savedStateHandle.contains(pokemonRecyclerStateKey)) {
            savedStateHandle.remove<List<PokemonSummary>>(pokemonRecyclerStateKey)
            return
        }
        if (this::job.isInitialized) {
            job.cancel()
        }
    }
}
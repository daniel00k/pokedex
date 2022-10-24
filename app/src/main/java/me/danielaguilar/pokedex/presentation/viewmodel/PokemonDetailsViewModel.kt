package me.danielaguilar.pokedex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.usecase.SearchPokemonUseCase
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val searchPokemonUseCase: SearchPokemonUseCase) :
    ViewModel() {
    lateinit var job: Job
    private val _viewState =
        MutableLiveData<PokedexIndexViewState<PokemonInfo>>(PokedexIndexViewState.Loading)
    val viewState: LiveData<PokedexIndexViewState<PokemonInfo>>
        get() = _viewState

    override fun onCleared() {
        super.onCleared()
        if (this::job.isInitialized) {
            job.cancel()
        }
    }

    fun getPokemonInfo(pokemonId: Int) {
        _viewState.postValue(PokedexIndexViewState.Loading)
        job = viewModelScope.launch(Dispatchers.IO) {
            val pokemon = searchPokemonUseCase.getById(pokemonId)
            withContext(Dispatchers.Main) {
                _viewState.postValue(PokedexIndexViewState.Success(pokemon))
            }
        }
    }
}
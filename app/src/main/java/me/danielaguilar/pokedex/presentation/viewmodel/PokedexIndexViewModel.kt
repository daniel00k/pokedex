package me.danielaguilar.pokedex.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.danielaguilar.pokedex.domain.*
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.usecase.GetAllPokemonUseCase
import me.danielaguilar.pokedex.usecase.GetPokemonUseCase
import me.danielaguilar.pokedex.usecase.SavePokemonUseCase
import me.danielaguilar.pokedex.usecase.SearchPokemonUseCase
import me.danielaguilar.pokedex.util.PokemonPropertiesExtractor
import me.danielaguilar.pokedex.util.extension.toDomainPokemonSummary
import javax.inject.Inject

@HiltViewModel
class PokedexIndexViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val savePokemonUseCase: SavePokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val pokemonRecyclerStateKey = "pokemons"
    private val recyclerStateKey = "recyclerState"
    private val _viewState =
        MutableLiveData<PokedexIndexViewState<List<PokemonSummary>>>(PokedexIndexViewState.Loading)
    val viewState: LiveData<PokedexIndexViewState<List<PokemonSummary>>>
        get() = _viewState
    private lateinit var job: Job
    private lateinit var jobSearch: Job

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

    fun searchPokemonByName(name: String) {
        _viewState.postValue(PokedexIndexViewState.Loading)
        jobSearch = viewModelScope.launch(Dispatchers.IO) {
            val pokemons = searchPokemonUseCase.getAllByName(name)
            withContext(Dispatchers.Main) {
                _viewState.postValue(PokedexIndexViewState.Success(pokemons))
            }
        }
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
                    val pokemons = response.body()!!.results.map { p -> p.toDomainPokemonSummary() }
                    _viewState.postValue(PokedexIndexViewState.Success(pokemons))
                    pokemons.map { p ->

                        val pokemonResponse = getPokemonUseCase.getPokemonInfo(p.id)
                        val pokemonEncounterResponse =
                            getPokemonUseCase.getPokemonEncounterInfo(p.id)
                        if (pokemonEncounterResponse.isSuccessful) {
                            val apiResponse = pokemonEncounterResponse.body()!!
                            savePokemonUseCase.savePokemonLocationsInfo(
                                pokemonId = p.id,
                                locations = apiResponse.map { e ->
                                    PokemonLocation(
                                        id = PokemonPropertiesExtractor().getIdFromUrl(
                                            e.locationArea.url
                                        ), name = e.locationArea.name
                                    )
                                })
                        }
                        if (pokemonResponse.isSuccessful) {
                            val apiResponse = pokemonResponse.body()!!
                            savePokemonUseCase.savePokemonInfo(
                                PokemonInfo(
                                    p.id,
                                    p.name,
                                    PokemonPropertiesExtractor().getImageUrlFromId(p.id),
                                    types = apiResponse.types.map { pk ->
                                        PokemonKind(
                                            PokemonPropertiesExtractor().getIdFromUrl(pk.type.url),
                                            pk.type.name
                                        )
                                    },
                                    attacks = apiResponse.moves.map { m ->
                                        PokemonAttack(
                                            m.move.name,
                                            PokemonPropertiesExtractor().getIdFromUrl(m.move.url)
                                        )
                                    },
                                    skills = apiResponse.abilities.map { a ->
                                        PokemonSkill(
                                            a.ability.name,
                                            id = PokemonPropertiesExtractor().getIdFromUrl(a.ability.url)
                                        )
                                    },
                                    locations = listOf()
                                )
                            )
                        }
                    }
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        _viewState.postValue(PokedexIndexViewState.Error(errorMessage = message))
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
        if (this::jobSearch.isInitialized) {
            jobSearch.cancel()
        }
    }
}
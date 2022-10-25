package me.danielaguilar.pokedex.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.danielaguilar.pokedex.data.model.pokedex.LocationAreaWrapper
import me.danielaguilar.pokedex.data.model.pokedex.PokemonApiResponse
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import retrofit2.Response
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun getPokemonInfo(id: Int): Flow<Response<PokemonApiResponse>> = flow {
        emit(pokedexRepository.fetchPokemonById(id))
    }

    suspend fun getPokemonEncounterInfo(id: Int): Flow<Response<List<LocationAreaWrapper>>> = flow {
        emit(pokedexRepository.fetchPokemonEncounters(id))
    }
}
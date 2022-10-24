package me.danielaguilar.pokedex.usecase

import me.danielaguilar.pokedex.data.model.pokedex.LocationAreaWrapper
import me.danielaguilar.pokedex.data.model.pokedex.PokemonApiResponse
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import retrofit2.Response
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun getPokemonInfo(id: Int): Response<PokemonApiResponse> {
        return pokedexRepository.fetchPokemonById(id)
    }

    suspend fun getPokemonEncounterInfo(id: Int): Response<List<LocationAreaWrapper>> {
        return pokedexRepository.fetchPokemonEncounters(id)
    }
}
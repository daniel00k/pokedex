package me.danielaguilar.pokedex.usecase

import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import retrofit2.Response
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun getAllPokemon(): Response<AllPokemonApiResponse> {
        return pokedexRepository.fetchAllPokemon()
    }
}
package me.danielaguilar.pokedex.data.repository

import me.danielaguilar.pokedex.data.datasource.remote.pokedex.PokedexApi
import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import retrofit2.Response
import javax.inject.Inject

class PokedexRepository @Inject constructor(private val pokedexApi: PokedexApi) {

    suspend fun fetchAllPokemon(): Response<AllPokemonApiResponse> {
        val limit = 151
        return pokedexApi.getAllPokemon(limit)
    }
}

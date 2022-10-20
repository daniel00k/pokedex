package me.danielaguilar.pokedex.data.datasource.remote.pokedex

import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokedexApi {
    @GET("api/v2/pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int,
    ): Response<AllPokemonApiResponse>
}
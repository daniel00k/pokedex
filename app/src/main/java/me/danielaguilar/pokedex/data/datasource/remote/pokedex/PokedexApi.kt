package me.danielaguilar.pokedex.data.datasource.remote.pokedex

import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import me.danielaguilar.pokedex.data.model.pokedex.LocationAreaWrapper
import me.danielaguilar.pokedex.data.model.pokedex.PokemonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {
    @GET("api/v2/pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int,
    ): Response<AllPokemonApiResponse>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int,
    ): Response<PokemonApiResponse>

    @GET("api/v2/pokemon/{id}/encounters")
    suspend fun getPokemonEncountersById(
        @Path("id") id: Int,
    ): Response<List<LocationAreaWrapper>>
}
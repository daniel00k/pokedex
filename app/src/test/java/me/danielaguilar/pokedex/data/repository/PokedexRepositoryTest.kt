package me.danielaguilar.pokedex.data.repository

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.datasource.local.pokedex.PokemonDatabase
import me.danielaguilar.pokedex.data.datasource.remote.pokedex.PokedexApi
import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PokedexRepositoryTest {
    @MockK
    lateinit var db: PokemonDatabase

    @MockK
    lateinit var pokedexApi: PokedexApi

    @Before
    fun seUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `fetchAllPokemons calls getAllPokemons`() {
        val response = mockk<Response<AllPokemonApiResponse>>()
        val repository = PokedexRepository(pokedexApi, db)
        val limit = 150
        coEvery {
            pokedexApi.getAllPokemon(limit)
        } returns response

        runBlocking {
            repository.fetchAllPokemon()
        }

        coVerify(exactly = 1) {
            repository.fetchAllPokemon()
        }

    }
}
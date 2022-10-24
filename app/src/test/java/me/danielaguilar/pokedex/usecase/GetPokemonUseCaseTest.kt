package me.danielaguilar.pokedex.usecase

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.model.pokedex.LocationAreaWrapper
import me.danielaguilar.pokedex.data.model.pokedex.PokemonApiResponse
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetPokemonUseCaseTest {

    @MockK
    lateinit var pokedexRepository: PokedexRepository

    @Before
    fun seUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `calls pokemon repository`() {
        val expected = mockk<Response<PokemonApiResponse>>()
        val id = 1
        coEvery { pokedexRepository.fetchPokemonById(id) } returns expected
        val getPokemonUseCase = GetPokemonUseCase(pokedexRepository)
        runBlocking {
            val actual = getPokemonUseCase.getPokemonInfo(id)
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) {
            pokedexRepository.fetchPokemonById(id)
        }
    }

    @Test
    fun `calls pokemon repository to get pokemon encounters`() {
        val expected = mockk<Response<List<LocationAreaWrapper>>>()
        val id = 1
        coEvery { pokedexRepository.fetchPokemonEncounters(id) } returns expected
        val getPokemonUseCase = GetPokemonUseCase(pokedexRepository)
        runBlocking {
            val actual = getPokemonUseCase.getPokemonEncounterInfo(id)
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) {
            pokedexRepository.fetchPokemonEncounters(id)

        }
    }
}
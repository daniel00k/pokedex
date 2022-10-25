package me.danielaguilar.pokedex.usecase

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetAllPokemonUseCaseTest {

    @MockK
    lateinit var pokedexRepository: PokedexRepository

    @Before
    fun seUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `calls pokemon repository`() {
        val expected = mockk<Response<AllPokemonApiResponse>>()
        coEvery { pokedexRepository.fetchAllPokemon() } returns expected
        val getAllPokemonUseCase = GetAllPokemonUseCase(pokedexRepository)
        runBlocking {
            getAllPokemonUseCase.getAllPokemon().collect { actual ->
                Assert.assertEquals(expected, actual)
            }
        }

        coVerify(exactly = 1) {
            pokedexRepository.fetchAllPokemon()
        }
    }
}
package me.danielaguilar.pokedex.usecase

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.entity.PokemonDetailedInformationEntity
import me.danielaguilar.pokedex.data.entity.PokemonEntity
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.util.extension.toPokemonInfo
import me.danielaguilar.pokedex.util.extension.toPokemonSummary
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchPokemonUseCaseTest {
    @MockK
    lateinit var pokedexRepository: PokedexRepository

    @Before
    fun seUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `calls pokemon repository`() {
        val name = "pikachu"
        val pokemonEntities = listOf(PokemonEntity(1, name, null))
        val expected = pokemonEntities.map { pe -> pe.toPokemonSummary() }
        coEvery { pokedexRepository.findPokemonByName(name) } returns pokemonEntities
        val searchPokemonUseCase = SearchPokemonUseCase(pokedexRepository)

        runBlocking {
            val actual = searchPokemonUseCase.getAllByName(name)
            Assert.assertEquals(expected.size, actual.size)
            Assert.assertEquals(expected[0].id, actual[0].id)
        }

        coVerify(exactly = 1) {
            pokedexRepository.findPokemonByName(
                name
            )
        }
    }

    @Test
    fun `calls pokemon repository to get a pokemon by id`() {
        val id = 1
        val pokemonEntity = PokemonDetailedInformationEntity(
            PokemonEntity(id, "pikachu", null),
            listOf(),
            listOf(),
            listOf(),
            listOf()
        )
        val expected = pokemonEntity.toPokemonInfo()
        coEvery { pokedexRepository.findPokemonById(id) } returns pokemonEntity
        val searchPokemonUseCase = SearchPokemonUseCase(pokedexRepository)

        runBlocking {
            val actual = searchPokemonUseCase.getById(id)
            Assert.assertEquals(expected.id, actual.id)
            Assert.assertEquals(expected.name, actual.name)
        }

        coVerify(exactly = 1) {
            pokedexRepository.findPokemonById(id)
        }
    }
}
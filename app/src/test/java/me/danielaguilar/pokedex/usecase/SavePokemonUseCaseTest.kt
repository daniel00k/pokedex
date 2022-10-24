package me.danielaguilar.pokedex.usecase

import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.entity.*
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.domain.PokemonLocation
import org.junit.Before
import org.junit.Test

class SavePokemonUseCaseTest {

    @MockK
    lateinit var pokedexRepository: PokedexRepository

    @Before
    fun seUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `calls pokemon repository`() {
        val pokemon = PokemonInfo(
            id = 1,
            name = "pikachu",
            imageUrl = "pikachuImage",
            types = listOf(),
            attacks = listOf(),
            skills = listOf(),
            locations = listOf()
        )

        val savePokemonUseCase = SavePokemonUseCase(pokedexRepository)

        runBlocking {
            savePokemonUseCase.savePokemonInfo(pokemon)
        }

        coVerify(exactly = 1) {
            pokedexRepository.savePokemon(
                any<PokemonEntity>(),
                any<List<SkillEntity>>(),
                any<List<AttackEntity>>(),
                any<List<PokemonKindEntity>>()
            )
        }
    }


    @Test
    fun `calls pokemon repository to store the locations`() {
        val pokemon = PokemonInfo(
            id = 1,
            name = "pikachu",
            imageUrl = "pikachuImage",
            types = listOf(),
            attacks = listOf(),
            skills = listOf(),
            locations = listOf()
        )

        val savePokemonUseCase = SavePokemonUseCase(pokedexRepository)
        val locations = listOf<PokemonLocation>()
        runBlocking {
            savePokemonUseCase.savePokemonLocationsInfo(locations, pokemon.id)
        }

        coVerify(exactly = 1) {
            pokedexRepository.savePokemonLocation(
                any<List<LocationEntity>>(),
                pokemon.id
            )
        }
    }
}
package me.danielaguilar.pokedex.usecase

import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.util.extension.*
import javax.inject.Inject

class SavePokemonUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun savePokemonInfo(pokemon: PokemonInfo) {
        return pokedexRepository.savePokemon(
            pokemon.toPokemonEntity(),
            pokemon.getPokemonSkillsEntity(),
            pokemon.getPokemonAttacksEntity(),
            pokemon.getPokemonLocationsEntity(),
            pokemon.getPokemonTypesEntity()
        )
    }
}
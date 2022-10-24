package me.danielaguilar.pokedex.usecase

import me.danielaguilar.pokedex.data.entity.LocationEntity
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.domain.PokemonLocation
import me.danielaguilar.pokedex.util.extension.getPokemonAttacksEntity
import me.danielaguilar.pokedex.util.extension.getPokemonSkillsEntity
import me.danielaguilar.pokedex.util.extension.getPokemonTypesEntity
import me.danielaguilar.pokedex.util.extension.toPokemonEntity
import javax.inject.Inject

class SavePokemonUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun savePokemonInfo(pokemon: PokemonInfo) {
        pokedexRepository.savePokemon(
            pokemon.toPokemonEntity(),
            pokemon.getPokemonSkillsEntity(),
            pokemon.getPokemonAttacksEntity(),
            pokemon.getPokemonTypesEntity()
        )
    }

    suspend fun savePokemonLocationsInfo(locations: List<PokemonLocation>, pokemonId: Int) {
        pokedexRepository.savePokemonLocation(
            locations.map { l ->
                LocationEntity(
                    name = l.name,
                    locationId = l.id
                )
            }, pokemonId
        )
    }
}
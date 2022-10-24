package me.danielaguilar.pokedex.usecase

import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.domain.PokemonSummary
import me.danielaguilar.pokedex.util.extension.toPokemonInfo
import me.danielaguilar.pokedex.util.extension.toPokemonSummary
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(private val repository: PokedexRepository) {
    suspend fun getAllByName(name: String): List<PokemonSummary> {
        return repository.findPokemonByName(name).map { pe -> pe.toPokemonSummary() }
    }

    suspend fun getById(id: Int): PokemonInfo {
        return repository.findPokemonById(id).toPokemonInfo()
    }
}
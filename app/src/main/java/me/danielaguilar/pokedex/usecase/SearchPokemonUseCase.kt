package me.danielaguilar.pokedex.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.danielaguilar.pokedex.data.repository.PokedexRepository
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.domain.PokemonSummary
import me.danielaguilar.pokedex.util.extension.toPokemonInfo
import me.danielaguilar.pokedex.util.extension.toPokemonSummary
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(private val repository: PokedexRepository) {
    suspend fun getAllByName(name: String): Flow<List<PokemonSummary>> = flow {
        val result = repository.findPokemonByName(name).map { pe -> pe.toPokemonSummary() }
        emit(result)
    }

    suspend fun getById(id: Int): Flow<PokemonInfo> = flow {
        emit(repository.findPokemonById(id).toPokemonInfo())
    }
}
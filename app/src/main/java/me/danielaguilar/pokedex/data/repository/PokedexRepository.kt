package me.danielaguilar.pokedex.data.repository

import me.danielaguilar.pokedex.data.datasource.local.pokedex.PokemonDatabase
import me.danielaguilar.pokedex.data.datasource.remote.pokedex.PokedexApi
import me.danielaguilar.pokedex.data.entity.*
import me.danielaguilar.pokedex.data.model.pokedex.AllPokemonApiResponse
import me.danielaguilar.pokedex.data.model.pokedex.PokemonApiResponse
import retrofit2.Response
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val pokedexApi: PokedexApi,
    private val db: PokemonDatabase
) {

    suspend fun fetchAllPokemon(): Response<AllPokemonApiResponse> {
        val limit = 151
        return pokedexApi.getAllPokemon(limit)
    }

    suspend fun fetchPokemonById(id: Int): Response<PokemonApiResponse> {
        return pokedexApi.getPokemonById(id)
    }

    suspend fun savePokemon(
        pokemonEntity: PokemonEntity,
        skills: List<SkillEntity>,
        attacks: List<AttackEntity>,
        locations: List<LocationEntity>,
        types: List<PokemonKindEntity>
    ) {
        db.pokemonDao().insertOne(pokemonEntity)
        skills.map { s ->
            db.skillDao().insertOne(s)
            db.pokemonAndSkillDao().insert(PokemonAndSkillEntity(pokemonEntity.id, s.skillId))
        }
        attacks.map { a ->
            db.attackDao().insertOne(a)
            db.pokemonAndAttackDao().insert(PokemonAndAttackEntity(pokemonEntity.id, a.attackId))
        }
        types.map { t ->
            db.kindDao().insertOne(t)
            db.pokemonAndKindDao().insert(PokemonAndKindEntity(pokemonEntity.id, t.kindId))
        }
    }
}

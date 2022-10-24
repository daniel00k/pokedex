package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.*
import me.danielaguilar.pokedex.data.entity.PokemonDetailedInformationEntity
import me.danielaguilar.pokedex.data.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%'")
    suspend fun getAllByName(name: String): List<PokemonEntity>

    @Query(
        "SELECT * FROM pokemon WHERE name LIKE :name LIMIT 1"
    )
    suspend fun findByName(name: String): PokemonEntity

    @Query(
        "SELECT * FROM pokemon WHERE pokemonId = :id LIMIT 1"
    )
    suspend fun findById(id: Int): PokemonDetailedInformationEntity

    @Insert
    suspend fun insertAll(vararg pokemons: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(pokemon: PokemonEntity)

}
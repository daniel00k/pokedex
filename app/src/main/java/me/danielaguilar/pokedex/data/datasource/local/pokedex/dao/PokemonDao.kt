package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.danielaguilar.pokedex.data.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>

    @Query(
        "SELECT * FROM pokemon WHERE name LIKE :name LIMIT 1"
    )
    suspend fun findByName(name: String): PokemonEntity

    @Query(
        "SELECT * FROM pokemon WHERE pokemonId = :id LIMIT 1"
    )
    suspend fun findById(id: Int): PokemonEntity

    @Insert
    suspend fun insertAll(vararg pokemons: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(pokemon: PokemonEntity)

}
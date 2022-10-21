package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.danielaguilar.pokedex.data.entity.PokemonKindEntity

@Dao
interface KindDao {

    @Query("SELECT * FROM kind")
    suspend fun getAll(): List<PokemonKindEntity>

    @Query(
        "SELECT * FROM kind WHERE kindId = :id LIMIT 1"
    )
    suspend fun findById(id: Int): PokemonKindEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(skill: PokemonKindEntity)

}
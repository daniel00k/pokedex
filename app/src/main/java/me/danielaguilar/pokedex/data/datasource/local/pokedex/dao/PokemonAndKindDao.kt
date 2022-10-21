package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import me.danielaguilar.pokedex.data.entity.PokemonAndKindEntity

@Dao
interface PokemonAndKindDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: PokemonAndKindEntity)
}
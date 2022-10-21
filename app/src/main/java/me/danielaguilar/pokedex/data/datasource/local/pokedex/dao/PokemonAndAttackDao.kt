package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import me.danielaguilar.pokedex.data.entity.PokemonAndAttackEntity

@Dao
interface PokemonAndAttackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: PokemonAndAttackEntity)
}

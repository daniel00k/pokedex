package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.danielaguilar.pokedex.data.entity.AttackEntity

@Dao
interface AttackDao {

    @Query("SELECT * FROM attack")
    suspend fun getAll(): List<AttackEntity>

    @Query(
        "SELECT * FROM attack WHERE attackId = :id LIMIT 1"
    )
    suspend fun findById(id: Int): AttackEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(skill: AttackEntity)

}
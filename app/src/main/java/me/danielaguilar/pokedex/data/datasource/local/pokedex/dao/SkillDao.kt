package me.danielaguilar.pokedex.data.datasource.local.pokedex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.danielaguilar.pokedex.data.entity.SkillEntity

@Dao
interface SkillDao {

    @Query("SELECT * FROM skill")
    suspend fun getAll(): List<SkillEntity>

    @Query(
        "SELECT * FROM skill WHERE skillId = :id LIMIT 1"
    )
    suspend fun findById(id: Int): SkillEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(skill: SkillEntity)

}
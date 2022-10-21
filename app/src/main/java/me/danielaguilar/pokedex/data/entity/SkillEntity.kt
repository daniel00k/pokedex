package me.danielaguilar.pokedex.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill")
class SkillEntity(
    @PrimaryKey
    @ColumnInfo("skillId")
    val skillId: Int,
    @ColumnInfo(name = "name")
    val name: String
)
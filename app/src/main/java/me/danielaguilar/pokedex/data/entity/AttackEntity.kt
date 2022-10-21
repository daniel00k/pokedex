package me.danielaguilar.pokedex.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attack")
class AttackEntity(
    @PrimaryKey
    @ColumnInfo("attackId")
    val attackId: Int,
    @ColumnInfo(name = "name")
    val name: String
)
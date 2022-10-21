package me.danielaguilar.pokedex.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kind")
class PokemonKindEntity(
    @PrimaryKey
    @ColumnInfo("kindId")
    val kindId: Int,
    @ColumnInfo(name = "name")
    val name: String
)
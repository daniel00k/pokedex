package me.danielaguilar.pokedex.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
class LocationEntity(
    @PrimaryKey
    @ColumnInfo("locationId")
    val locationId: Int,
    @ColumnInfo(name = "name")
    val name: String
)
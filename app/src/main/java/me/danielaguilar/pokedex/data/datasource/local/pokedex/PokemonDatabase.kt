package me.danielaguilar.pokedex.data.datasource.local.pokedex

import androidx.room.Database
import androidx.room.RoomDatabase
import me.danielaguilar.pokedex.data.datasource.local.pokedex.dao.*
import me.danielaguilar.pokedex.data.entity.*

@Database(
    entities = [PokemonEntity::class, AttackEntity::class, LocationEntity::class,
        PokemonKindEntity::class, SkillEntity::class, PokemonAndSkillEntity::class,
        PokemonAndAttackEntity::class, PokemonAndKindEntity::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun skillDao(): SkillDao
    abstract fun attackDao(): AttackDao
    abstract fun kindDao(): KindDao
    abstract fun pokemonAndSkillDao(): PokemonAndSkillDao
    abstract fun pokemonAndAttackDao(): PokemonAndAttackDao
    abstract fun pokemonAndKindDao(): PokemonAndKindDao
}
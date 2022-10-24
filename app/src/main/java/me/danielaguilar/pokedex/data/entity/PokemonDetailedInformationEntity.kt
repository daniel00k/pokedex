package me.danielaguilar.pokedex.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PokemonDetailedInformationEntity(
    @Embedded
    val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "pokemonId",
        entity = PokemonKindEntity::class,
        entityColumn = "kindId",
        associateBy = Junction(
            value = PokemonAndKindEntity::class,
            parentColumn = "pokemonId",
            entityColumn = "kindId"
        )
    )
    val kind: List<PokemonKindEntity>,
    @Relation(
        parentColumn = "pokemonId",
        entity = AttackEntity::class,
        entityColumn = "attackId",
        associateBy = Junction(
            value = PokemonAndAttackEntity::class,
            parentColumn = "pokemonId",
            entityColumn = "attackId"
        )
    )
    val attacks: List<AttackEntity>,
    @Relation(
        parentColumn = "pokemonId",
        entity = SkillEntity::class,
        entityColumn = "skillId",
        associateBy = Junction(
            value = PokemonAndSkillEntity::class,
            parentColumn = "pokemonId",
            entityColumn = "skillId"
        )
    )
    val skills: List<SkillEntity>
)
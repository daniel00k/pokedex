package me.danielaguilar.pokedex.util.extension

import me.danielaguilar.pokedex.data.entity.*
import me.danielaguilar.pokedex.data.model.pokedex.PokemonSummary
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.util.PokemonPropertiesExtractor


fun PokemonSummary.toDomainPokemonSummary() =
    me.danielaguilar.pokedex.domain.PokemonSummary(
        name = this.name,
        id = PokemonPropertiesExtractor().getIdFromUrl(this.url),
        imageUrl = PokemonPropertiesExtractor().getImageUrlFromUrl(url)
    )

fun PokemonInfo.toPokemonEntity() =
    PokemonEntity(
        name = this.name,
        id = this.id,
        evolution = null,
    )

fun PokemonInfo.getPokemonSkillsEntity() =
    this.skills.map { s -> SkillEntity(skillId = s.id, name = s.name) }

fun PokemonInfo.getPokemonAttacksEntity() =
    this.attacks.map { a -> AttackEntity(name = a.name, attackId = a.id) }

fun PokemonInfo.getPokemonLocationsEntity() =
    this.locations.map { l -> LocationEntity(name = l.name) }

fun PokemonInfo.getPokemonTypesEntity() =
    this.types.map { t -> PokemonKindEntity(name = t.name, kindId = t.id) }


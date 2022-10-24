package me.danielaguilar.pokedex.util.extension

import me.danielaguilar.pokedex.data.entity.*
import me.danielaguilar.pokedex.data.model.pokedex.PokemonSummary
import me.danielaguilar.pokedex.domain.*
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

fun PokemonEntity.toPokemonSummary() =
    me.danielaguilar.pokedex.domain.PokemonSummary(
        name = this.name,
        id = this.id,
        imageUrl = PokemonPropertiesExtractor().getImageUrlFromId(this.id)
    )

fun PokemonDetailedInformationEntity.toPokemonInfo() = PokemonInfo(
    id = this.pokemon.id,
    name = this.pokemon.name,
    imageUrl = PokemonPropertiesExtractor().getImageUrlFromId(this.pokemon.id),
    evolution = this.pokemon.evolution,
    types = this.kind.map { pke -> PokemonKind(pke.kindId, pke.name) },
    attacks = this.attacks.map { a -> PokemonAttack(a.name, a.attackId) },
    skills = this.skills.map { s -> PokemonSkill(s.name, s.skillId) },
    locations = this.locations.map { l -> PokemonLocation(l.locationId, l.name) }
)

fun PokemonInfo.getPokemonSkillsEntity() =
    this.skills.map { s -> SkillEntity(skillId = s.id, name = s.name) }

fun PokemonInfo.getPokemonAttacksEntity() =
    this.attacks.map { a -> AttackEntity(name = a.name, attackId = a.id) }

fun PokemonInfo.getPokemonLocationsEntity() =
    this.locations.map { l -> LocationEntity(name = l.name, locationId = l.id) }

fun PokemonInfo.getPokemonTypesEntity() =
    this.types.map { t -> PokemonKindEntity(name = t.name, kindId = t.id) }


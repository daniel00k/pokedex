package me.danielaguilar.pokedex.util.extension

import me.danielaguilar.pokedex.data.model.pokedex.PokemonSummary
import me.danielaguilar.pokedex.util.PokemonPropertiesExtractor


fun PokemonSummary.toDomainPokemonSummary() =
    me.danielaguilar.pokedex.domain.PokemonSummary(
        name = this.name,
        id = PokemonPropertiesExtractor().getIdFromUrl(this.url),
        imageUrl = PokemonPropertiesExtractor().getImageUrlFromUrl(url)
    )


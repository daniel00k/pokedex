package me.danielaguilar.pokedex.util

import me.danielaguilar.pokedex.BuildConfig
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PokemonPropertiesExtractorTest {
    private val pokemonPropertiesExtractor = PokemonPropertiesExtractor()

    @Test
    fun `gets the id by reading the url`() {
        val url = "https://pokeapi.co/api/v2/pokemon/1/"
        val expected = 1
        val actual = pokemonPropertiesExtractor.getIdFromUrl(url)
        assertEquals(expected, actual)
    }

    @Test
    fun `gets image url using the id`() {
        val id = 1
        val expected = "${BuildConfig.BASE_POKEMON_IMAGE_URL}/${id}.png"
        val actual = pokemonPropertiesExtractor.getImageUrlFromId(id)
        assertEquals(expected, actual)
    }

    @Test
    fun `gets image url using the url`() {
        val id = 1
        val url = "https://pokeapi.co/api/v2/pokemon/${id}/"
        val expected = "${BuildConfig.BASE_POKEMON_IMAGE_URL}/${id}.png"
        val actual = pokemonPropertiesExtractor.getImageUrlFromUrl(url)
        assertEquals(expected, actual)
    }
}
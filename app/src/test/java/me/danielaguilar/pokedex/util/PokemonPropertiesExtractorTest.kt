package me.danielaguilar.pokedex.util

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
}
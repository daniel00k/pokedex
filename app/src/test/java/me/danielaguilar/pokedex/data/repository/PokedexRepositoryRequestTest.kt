package me.danielaguilar.pokedex.data.repository

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import me.danielaguilar.pokedex.data.datasource.local.pokedex.PokemonDatabase
import me.danielaguilar.pokedex.data.datasource.remote.pokedex.PokedexApi
import me.danielaguilar.pokedex.data.model.pokedex.*
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PokedexRepositoryRequestTest {
    @MockK
    lateinit var db: PokemonDatabase
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokedexApi::class.java)

    private lateinit var repository: PokedexRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PokedexRepository(api, db)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch pokemons correctly`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                "{\n" +
                        "    \"results\": [\n" +
                        "        { \"name\": \"bulbasaur\", \"url\": \"https://pokeapi.co/api/v2/pokemon/1/\" }\n" +
                        "    ]\n" +
                        "}"
            )
        )

        runBlocking {
            val actual = repository.fetchAllPokemon().body()
            val expected = AllPokemonApiResponse(
                listOf(PokemonSummary("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should fetch pokemon by id`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                "{\n" +
                        "    \"abilities\": [{\n" +
                        "        \"ability\": {\n" +
                        "            \"name\": \"swift-swim\",\n" +
                        "            \"url\": \"https://pokeapi.co/api/v2/ability/33/\"\n" +
                        "        },\n" +
                        "        \"is_hidden\": false,\n" +
                        "        \"slot\": 1\n" +
                        "    }],\n" +
                        "    \"moves\": [{\n" +
                        "        \"move\": {\n" +
                        "            \"name\": \"bind\",\n" +
                        "            \"url\": \"https://pokeapi.co/api/v2/move/20/\"\n" +
                        "        }\n" +
                        "    }],\n" +
                        "    \"name\": \"omastar\",\n" +
                        "    \"types\": [{\n" +
                        "            \"slot\": 1,\n" +
                        "            \"type\": {\n" +
                        "                \"name\": \"rock\",\n" +
                        "                \"url\": \"https://pokeapi.co/api/v2/type/6/\"\n" +
                        "            }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"slot\": 2,\n" +
                        "            \"type\": {\n" +
                        "                \"name\": \"water\",\n" +
                        "                \"url\": \"https://pokeapi.co/api/v2/type/11/\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"weight\": 350\n" +
                        "}"
            )
        )

        runBlocking {
            val id = 1
            val actual = repository.fetchPokemonById(id).body()
            val expected = PokemonApiResponse(
                listOf(
                    AbilityWrapper(
                        Skill("swift-swim", "https://pokeapi.co/api/v2/ability/33/")
                    )
                ),
                listOf(
                    MoveWrapper(
                        Attack(
                            "bind", "https://pokeapi.co/api/v2/move/20/"
                        )
                    )
                ),
                listOf(
                    PokemonKindWrapper(
                        PokemonKind(
                            "https://pokeapi.co/api/v2/type/6/", "rock"
                        )
                    ),
                    PokemonKindWrapper(
                        PokemonKind(
                            "https://pokeapi.co/api/v2/type/11/", "water")
                    )
                )
            )
            assertEquals(expected, actual)
        }
    }

}
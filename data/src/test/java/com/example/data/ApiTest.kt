package com.example.data

import com.example.data.model.SearchResponse
import com.example.data.remote.Api
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: Api

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Test
    fun `getSearch should return data`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
        {
          "cards": [
            {
              "id": "swsh4-175",
              "name": "Drapion V",
              "nationalPokedexNumber": 452,
              "imageUrl": "https://images.pokemontcg.io/swsh4/175.png",
              "imageUrlHiRes": "https://images.pokemontcg.io/swsh4/175_hires.png",
              "types": ["Darkness"],
              "supertype": "Pokémon",
              "subtype": "V",
              "hp": "210",
              "retreatCost": ["Colorless", "Colorless", "Colorless"],
              "convertedRetreatCost": 3,
              "number": "175",
              "artist": "Eske Yoshinob",
              "rarity": "Rare Holo V",
              "series": "Sword & Shield",
              "set": "Vivid Voltage",
              "setCode": "swsh4",
              "text": ["V rule: When your Pokémon V is Knocked Out, your opponent takes 2 Prize cards."],
              "attacks": [
                {
                  "cost": ["Darkness", "Colorless", "Colorless"],
                  "name": "Wrack Down",
                  "text": "",
                  "damage": "70",
                  "convertedEnergyCost": 3
                },
                {
                  "cost": ["Darkness", "Colorless", "Colorless", "Colorless"],
                  "name": "Hazardous Claws",
                  "text": "Discard 2 Energy from this Pokémon. Your opponent's Active Pokémon is now Paralyzed and Poisoned.",
                  "damage": "130",
                  "convertedEnergyCost": 4
                }
              ],
              "weaknesses": [
                {
                  "type": "Fighting",
                  "value": "×2"
                }
              ]
            }
          ]
        }
    """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        val response: Response<SearchResponse> = api.getSearch("gte$100")

        assert(response.isSuccessful)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
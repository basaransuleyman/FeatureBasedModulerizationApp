package com.example.data

import com.example.core.model.Ability
import com.example.core.model.Attack
import com.example.core.model.Card
import com.example.core.model.Search
import com.example.core.model.Weaknesse
import com.example.core.util.Resource
import com.example.data.mapper.toSearch
import com.example.data.model.AbilityResponse
import com.example.data.model.AncientTraitResponse
import com.example.data.model.AttackResponse
import com.example.data.model.CardResponse
import com.example.data.model.ResistanceResponse
import com.example.data.model.SearchResponse
import com.example.data.model.WeaknesseResponse
import com.example.data.remote.datasource.SearchDataSource
import com.example.data.repository.SearchRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class SearchRepositoryImplTest {

    private lateinit var dataSource: SearchDataSource
    private lateinit var repository: SearchRepositoryImpl

    val exampleCardResponse = CardResponse(
        ability = AbilityResponse(
            name = "Damage Switch",
            text = "As often as you like during your turn...",
            type = "Poké-Power"
        ),
        ancientTrait = AncientTraitResponse(
            name = "Ancient Trait Name",
            text = "Ancient Trait Description"
        ),
        artist = "Ryo Ueda",
        attacks = listOf(
            AttackResponse(
                convertedEnergyCost = 3,
                cost = listOf("Darkness", "Colorless", "Colorless"),
                damage = "70",
                name = "Wrack Down",
                text = ""
            )
        ),
        convertedRetreatCost = 3,
        evolvesFrom = "Previous Pokemon",
        hp = "210",
        id = "swsh4-175",
        imageUrl = "https://images.pokemontcg.io/swsh4/175.png",
        imageUrlHiRes = "https://images.pokemontcg.io/swsh4/175_hires.png",
        name = "Drapion V",
        nationalPokedexNumber = 452,
        number = "175",
        rarity = "Rare Holo V",
        resistances = listOf(
            ResistanceResponse(
                type = "Resistance Type",
                value = "Resistance Value"
            )
        ),
        retreatCost = listOf("Colorless", "Colorless", "Colorless"),
        series = "Sword & Shield",
        set = "Vivid Voltage",
        setCode = "swsh4",
        subtype = "V",
        supertype = "Pokémon",
        text = listOf("V rule: When your Pokémon V is Knocked Out, your opponent takes 2 Prize cards."),
        types = listOf("Darkness"),
        weaknesses = listOf(
            WeaknesseResponse(
                type = "Fighting",
                value = "×2"
            )
        )
    )
    @Before
    fun setup() {
        dataSource = mock(SearchDataSource::class.java)
        repository = SearchRepositoryImpl(dataSource)

    }

    @Test
    fun `return Success when dataSource returns Success`() = runBlocking {

        val exampleSearchResponse = SearchResponse(
            searchCards = listOf(exampleCardResponse)
        )
        val fakeResponse = Response.success(exampleSearchResponse)

        val card = Card(
            id = "1",
            hp = "100",
            imageUrl = "http://example.com/image.jpg",
            name = "CardName",
            types = listOf("Type1", "Type2"),
            weaknesses = listOf(Weaknesse(type = "Some Type")),
            attacks = listOf(Attack(name = "Some Attack", text = "Some Text")),
            ability = Ability(name = "Some Ability"),
            artist = "ArtistName"
        )


        val mappedSearch = Search(listOf(card))
        val healthPoint = 100


        `when`(dataSource.getSearch(healthPoint)).thenReturn(fakeResponse)
        `when`(exampleSearchResponse.toSearch()).thenReturn(mappedSearch)

        val result = SearchRepositoryImpl(dataSource).invoke(healthPoint)

        assertTrue(result is Resource.Success)
        assertEquals(mappedSearch, (result as Resource.Success).data)
    }

}
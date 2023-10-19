import com.example.data.model.AbilityResponse
import com.example.data.model.AncientTraitResponse
import com.example.data.model.AttackResponse
import com.example.data.model.CardResponse
import com.example.data.model.ResistanceResponse
import com.example.data.model.SearchResponse
import com.example.data.model.WeaknesseResponse
import com.example.data.remote.Api
import com.example.data.remote.datasource.SearchDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

class SearchDataSourceImplTest {

    private lateinit var api: Api
    private lateinit var dataSource: SearchDataSourceImpl

    @Before
    fun setUp() {
        api = mock(Api::class.java)
        dataSource = SearchDataSourceImpl(api)
    }

    @Test
    fun getSearchTest() = runBlocking {
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
        val exampleSearchResponse = SearchResponse(
            searchCards = listOf(exampleCardResponse)
        )
        val exampleResponse = Response.success(exampleSearchResponse)

        Mockito.`when`(api.getSearch("gte100")).thenReturn(exampleResponse)

        val result = dataSource.getSearch(100)

        assertEquals(result, exampleResponse)
    }
}
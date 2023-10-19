package com.example.feature.search.ui

import com.example.core.model.Ability
import com.example.core.model.Attack
import com.example.core.model.Card
import com.example.core.model.Search
import com.example.core.model.Weaknesse
import com.example.core.util.MainCoroutineRule
import com.example.core.util.Resource
import com.example.domain.usecase.GetSearchUseCase
import com.example.feature.search.ui.util.SearchStatusMessage
import com.example.feature.search.ui.viewmodel.SearchViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val testDispatcher = TestCoroutineDispatcher(testCoroutineScheduler)

    @get:Rule
    val mainTestCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var getSearchUseCase: GetSearchUseCase
    private lateinit var searchViewModel: SearchViewModel

    private val card = Card(
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

    @Before
    fun setup() {
        getSearchUseCase = Mockito.mock(GetSearchUseCase::class.java)
        searchViewModel = SearchViewModel(getSearchUseCase)
    }

    @Test
    fun `getSearchResult when search is successful with isBackStackEntry false `() =
        mainTestCoroutineRule.runBlockingTest {
            val search = Search(listOf(card))
            val searchInitialData = Search(search.searchCards)


            Mockito.`when`(getSearchUseCase.invoke(100))
                .thenReturn(Resource.Success(searchInitialData))

            searchViewModel.getSearchResult(100, false)
            val cardsResult = searchViewModel.cards.first()
            val messageResult = searchViewModel.message.first()

            assertEquals(SearchStatusMessage.SEARCH_SUCCESS, messageResult)
            assertEquals(search.searchCards, cardsResult)
        }

    @Test
    fun `getSearchResult when search fails`() = mainTestCoroutineRule.runBlockingTest {
        Mockito.`when`(getSearchUseCase.invoke(100))
            .thenReturn(Resource.Failure(IOException("Error")))

        searchViewModel.getSearchResult(100, false)
        val messageResult = searchViewModel.message.first()

        assertEquals(SearchStatusMessage.SEARCH_FAILURE, messageResult)
    }

    @Test
    fun `getSearchResult when list is empty`() = mainTestCoroutineRule.runBlockingTest {
        val emptySearch = Search(emptyList())
        Mockito.`when`(getSearchUseCase.invoke(100)).thenReturn(Resource.Success(emptySearch))

        searchViewModel.getSearchResult(100, false)
        val cardsResult = searchViewModel.cards.first()
        val messageResult = searchViewModel.message.first()

        assertTrue(cardsResult.isEmpty())
        assertEquals(SearchStatusMessage.EMPTY_LIST, messageResult)
    }

    @Test
    fun `getSearchResult when isBackStackEntry is true`() = mainTestCoroutineRule.runBlockingTest {
        val search = Search(listOf(card))
        Mockito.`when`(getSearchUseCase.invoke(100)).thenReturn(Resource.Success(search))

        searchViewModel.getSearchResult(100, true)
        val messageResult = searchViewModel.message.first()

        assertEquals(SearchStatusMessage.SEARCH_SUCCESS_FROM_BACK_STACK_ENTRY, messageResult)
    }

}
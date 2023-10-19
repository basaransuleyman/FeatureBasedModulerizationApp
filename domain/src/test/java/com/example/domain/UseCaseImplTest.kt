package com.example.domain

import com.example.core.model.Ability
import com.example.core.model.Attack
import com.example.core.model.Card
import com.example.core.model.Search
import com.example.core.model.Weaknesse
import com.example.core.util.MainCoroutineRule
import com.example.core.util.Resource
import com.example.domain.repository.SearchRepository
import com.example.domain.usecase.GetSearchUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UseCaseImplTest {

    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val testDispatcher = TestCoroutineDispatcher(testCoroutineScheduler)

    @get:Rule
    val mainTestCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var repository: SearchRepository
    private lateinit var getSearchUseCase: GetSearchUseCaseImpl


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
    fun setUp() {
        repository = mock()
        getSearchUseCase = GetSearchUseCaseImpl(repository, testDispatcher)
    }

    @Test
    fun `invoke returns Success when repository returns Success`() = mainTestCoroutineRule.runBlockingTest {
        val healthPoint = 100
        val search = Search(listOf(card))
        val resource = Resource.Success(search)

        whenever(repository.invoke(healthPoint)).thenReturn(resource)

        val result = getSearchUseCase(healthPoint)

        assertEquals(resource, result)
        verify(repository).invoke(healthPoint)
    }

}
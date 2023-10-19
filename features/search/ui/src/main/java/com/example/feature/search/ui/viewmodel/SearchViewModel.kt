package com.example.feature.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.extensions.onFailure
import com.example.core.extensions.onSuccess
import com.example.core.model.Card
import com.example.core.model.Search
import com.example.domain.usecase.GetSearchUseCase
import com.example.feature.search.ui.util.SearchStatusMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards = _cards.asStateFlow()

    private val _message = MutableStateFlow<SearchStatusMessage>(SearchStatusMessage.NO_RESULT)
    val message: StateFlow<SearchStatusMessage> = _message

    private val _healthPoint = MutableStateFlow("")
    val healthPoint = _healthPoint.asStateFlow()

    private val _backStackFlag = MutableStateFlow(false)
    val backStackFlag = _backStackFlag.asStateFlow()

    fun getSearchResult(healthPoint: Int, isBackStackEntry: Boolean) {
        viewModelScope.launch {
            _message.update { SearchStatusMessage.LOADING }
            getDataFromSearch(healthPoint, isBackStackEntry)
        }
    }

    private suspend fun getDataFromSearch(healthPoint: Int, isBackStackEntry: Boolean) {
        getSearchUseCase.invoke(healthPoint)
            .onSuccess { searchInitialData ->
                setMessageFromBackStackEntry(isBackStackEntry, searchInitialData.searchCards)
                checkListSize(searchInitialData, searchInitialData.searchCards)
            }.onFailure { exception ->
                isInitialSearchFailure(exception)
            }
    }

    private fun setMessageFromBackStackEntry(isBackStackEntry: Boolean, cards: List<Card>) {
        if (isBackStackEntry) {
            _message.update { SearchStatusMessage.SEARCH_SUCCESS_FROM_BACK_STACK_ENTRY }
        } else if (!isBackStackEntry && cards.isNotEmpty()) {
            _message.update { SearchStatusMessage.SEARCH_SUCCESS }
        }
    }

    private suspend fun checkListSize(searchInitialData: Search, cards: List<Card>) {
        if (cards.isNotEmpty()) {
            _cards.emit(searchInitialData.searchCards)
        } else {
            _cards.emit(emptyList())
            _message.update { SearchStatusMessage.EMPTY_LIST }
        }
    }

    private fun isInitialSearchFailure(exception: IOException) {
        exception.let {
            _message.update { SearchStatusMessage.SEARCH_FAILURE }
        }
    }

    fun setCustomBackStackFlag(isBackStackEntry: Boolean) {
        _backStackFlag.update { isBackStackEntry }
    }

    fun setCustomHealthPoint(healthPointFromStack: String) {
        _healthPoint.update { healthPointFromStack }
    }

}
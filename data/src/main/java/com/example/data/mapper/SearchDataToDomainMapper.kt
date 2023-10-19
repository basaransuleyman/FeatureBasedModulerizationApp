package com.example.data.mapper

import com.example.core.model.Search
import com.example.data.model.SearchResponse
import javax.inject.Inject

fun SearchResponse.toSearch(): Search {
    return Search(searchCards = this.searchCards.map { it.toCard() })
}
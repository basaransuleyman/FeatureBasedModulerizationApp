package com.example.data.mapper

import com.example.core.model.Search
import com.example.data.model.SearchResponse
import javax.inject.Inject

class SearchDataToDomainMapper @Inject constructor(
    private val cardMapper: CardMapper
) : Mapper<SearchResponse, Search> {
    override fun map(from: SearchResponse): Search {
        return Search(searchCards = from.searchCards.map {
            cardMapper.map(
                it
            )
        })
    }
}

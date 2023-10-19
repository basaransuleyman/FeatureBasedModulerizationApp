package com.example.data.model

import com.example.core.ResponseModel
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("cards")
    val searchCards: List<CardResponse>
): ResponseModel

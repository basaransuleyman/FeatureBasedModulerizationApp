package com.example.core.model

import com.example.core.DomainModel

data class Search(
    val searchCards: List<Card>
): DomainModel

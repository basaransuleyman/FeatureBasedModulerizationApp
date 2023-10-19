package com.example.data.model

import com.example.core.ResponseModel

data class CardResponse(
    val ability: AbilityResponse,
    val ancientTrait: AncientTraitResponse,
    val artist: String,
    val attacks: List<AttackResponse>?,
    val convertedRetreatCost: Int,
    val evolvesFrom: String,
    val hp: String,
    val id: String,
    val imageUrl: String,
    val imageUrlHiRes: String,
    val name: String,
    val nationalPokedexNumber: Int,
    val number: String,
    val rarity: String,
    val resistances: List<ResistanceResponse>,
    val retreatCost: List<String>,
    val series: String,
    val `set`: String,
    val setCode: String,
    val subtype: String,
    val supertype: String,
    val text: List<String>,
    val types: List<String>,
    val weaknesses: List<WeaknesseResponse>?
) : ResponseModel

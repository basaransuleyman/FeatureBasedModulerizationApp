package com.example.data.mapper

import com.example.core.model.Card
import com.example.data.model.CardResponse
import javax.inject.Inject

class CardMapper @Inject constructor(
    private val weaknessMapper: WeaknessMapper,
    private val attackMapper: AttackMapper,
    private val abilityMapper: AbilityMapper
) : Mapper<CardResponse, Card> {
    override fun map(from: CardResponse): Card {
        return Card(
            id = from.id,
            hp = from.hp,
            imageUrl = from.imageUrl,
            name = from.name,
            types = from.types,
            weaknesses = from.weaknesses?.map { weaknessMapper.map(it) } ?: listOf(),
            attacks = from.attacks?.map { attackMapper.map(it) } ?: listOf(),
            ability = abilityMapper.map(from.ability),
            artist = from.artist
        )
    }
}
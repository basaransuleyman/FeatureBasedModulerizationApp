package com.example.data.mapper

import com.example.core.model.Card
import com.example.data.model.CardResponse
import javax.inject.Inject

fun CardResponse.toCard(
): Card {
    return Card(
        id = this.id,
        hp = this.hp,
        imageUrl = this.imageUrl,
        name = this.name,
        types = this.types,
        weaknesses = this.weaknesses?.map { it.mapToWeaknesse() } ?: listOf(),
        attacks = this.attacks?.map { it.mapToAttack() } ?: listOf(),
        ability = this.ability.mapToAbility(),
        artist = this.artist
    )
}
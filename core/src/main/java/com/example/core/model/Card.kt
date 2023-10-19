package com.example.core.model

import com.example.core.DomainModel

data class Card(
    val id: String,
    val hp: String,
    val imageUrl: String,
    val name: String,
    val types: List<String>,
    val weaknesses: List<Weaknesse>?,
    val attacks: List<Attack>?,
    val ability: Ability?,
    val artist: String
): DomainModel
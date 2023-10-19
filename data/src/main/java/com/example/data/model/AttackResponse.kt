package com.example.data.model

import com.example.core.ResponseModel

data class AttackResponse(
    val convertedEnergyCost: Int,
    val cost: List<String>,
    val damage: String,
    val name: String,
    val text: String
): ResponseModel
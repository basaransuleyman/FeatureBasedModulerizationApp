package com.example.data.mapper

import com.example.core.model.Ability
import com.example.data.model.AbilityResponse

fun AbilityResponse?.mapToAbility(): Ability? {
    return if (this != null) {
        Ability(name = this.name)
    } else {
        null
    }
}
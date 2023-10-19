package com.example.data.mapper

import com.example.core.model.Ability
import com.example.data.model.AbilityResponse
import javax.inject.Inject

class AbilityMapper @Inject constructor() : Mapper<AbilityResponse?, Ability?> {
    override fun map(from: AbilityResponse?): Ability? {
        return if (from != null) {
            Ability(name = from.name)
        } else {
            null
        }
    }
}
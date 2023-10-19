package com.example.data.mapper

import com.example.core.model.Attack
import com.example.data.model.AttackResponse
import javax.inject.Inject

class AttackMapper @Inject constructor() : Mapper<AttackResponse,Attack> {
    override fun map(from: AttackResponse): Attack {
        return Attack(
            name = from.name,
            text = from.text
        )
    }
}
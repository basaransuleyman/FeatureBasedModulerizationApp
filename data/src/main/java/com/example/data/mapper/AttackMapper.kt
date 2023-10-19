package com.example.data.mapper

import com.example.core.model.Attack
import com.example.data.model.AttackResponse

fun AttackResponse.mapToAttack(): Attack {
    return Attack(
        name = this.name,
        text = this.text
    )
}
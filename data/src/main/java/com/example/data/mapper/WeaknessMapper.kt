package com.example.data.mapper

import com.example.core.model.Weaknesse
import com.example.data.model.WeaknesseResponse

fun WeaknesseResponse.mapToWeaknesse(): Weaknesse {
    return Weaknesse(type = this.type)
}

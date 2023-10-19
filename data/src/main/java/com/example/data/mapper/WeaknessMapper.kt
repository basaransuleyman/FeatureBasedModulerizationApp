package com.example.data.mapper

import com.example.core.model.Weaknesse
import com.example.data.model.WeaknesseResponse
import javax.inject.Inject

class WeaknessMapper @Inject constructor() : Mapper<WeaknesseResponse, Weaknesse> {
    override fun map(from: WeaknesseResponse): Weaknesse {
        return Weaknesse(type = from.type)
    }
}
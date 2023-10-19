package com.example.navigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardUIModel(
    val artistName: String,
    val name: String,
    val image: String,
    val hp: String
) : Parcelable
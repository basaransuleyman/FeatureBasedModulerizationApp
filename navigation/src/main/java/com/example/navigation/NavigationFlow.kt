package com.example.navigation

import com.example.core.model.Card

sealed class NavigationFlow {
    object SearchFlow : NavigationFlow()
    data class DetailFlow(val card: Card) : NavigationFlow()
}
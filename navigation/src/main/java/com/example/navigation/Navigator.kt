package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.core.model.Card

class Navigator {

    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.SearchFlow -> navController.navigate(MainNavGraphDirections.actionSearchFlow())

        is NavigationFlow.DetailFlow -> navController.navigate(
            MainNavGraphDirections.actionDetailFlow(
                card = navigationFlow.card.mapToParcelable(),
            ),
            navOptions {
                restoreState = true
                launchSingleTop = true
            }
        )

    }

    private fun Card.mapToParcelable(): CardUIModel {
        return CardUIModel(
            artistName = this.artist,
            name = this.name,
            image = this.imageUrl,
            hp = this.hp
        )
    }
}
package com.example.prc_pokemon.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prc_pokemon.ui.screens.CharactersListScreen.CharacterListScreenAppMain
import com.example.prc_pokemon.ui.screens.EpisodesListScreen.EpisodesListAppMain
import com.example.prc_pokemon.ui.screens.LocationsListScreen.LocationsAppMain
import com.example.prc_pokemon.ui.screens.mainScreen.MainApp


/**Controladora de la navegacion para la pantalla MainScreen y
 * poder dirigirse a las pantallas que esta pueda derivar.*/
@Composable
fun NavigationAppMain(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Main.name,
        modifier = modifier
    ) {
        composable(
            route = Routes.Main.name,
            enterTransition = {
                slideIntoContainer(
                    SlideDirection.End,
                    tween(1000)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    SlideDirection.Start,
                    tween(1000)
                )
            }
        ) {
            MainApp(
                modifier = modifier
                    .fillMaxSize(),
                onGoToScreen = { onSelectedIndex ->
                    when (onSelectedIndex) {
                        0 -> navController.navigate(route = Routes.Characters_Route.name)
                        1 -> navController.navigate(route = Routes.Locations_Route.name)
                        2 -> navController.navigate(route = Routes.Episodes_Route.name)
                    }
                }
            )
        }

        composable(
            route = Routes.Characters_Route.name,
            enterTransition = {
                slideIntoContainer(
                    SlideDirection.Start,
                    tween(1000)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    SlideDirection.End,
                    tween(1000)
                )
            }) {
            CharacterListScreenAppMain()
        }

        composable(
            route = Routes.Locations_Route.name,
            enterTransition = {
                slideIntoContainer(
                    SlideDirection.Start,
                    tween(1000)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    SlideDirection.End,
                    tween(1000)
                )
            }
        ) {
            LocationsAppMain()
        }

        composable(
            route = Routes.Episodes_Route.name,
            enterTransition = {
                slideIntoContainer(
                    SlideDirection.Start,
                    tween(1000)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    SlideDirection.End,
                    tween(1000)
                )
            }
        ) {
            EpisodesListAppMain()
        }
    }
}
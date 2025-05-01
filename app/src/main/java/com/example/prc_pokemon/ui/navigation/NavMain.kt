package com.example.prc_pokemon.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prc_pokemon.ui.screens.CharactersListScreen.CharacterListScreenAppMain
import com.example.prc_pokemon.ui.screens.EpisodesListScreen.EpisodeSelectedResultScreen
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
        //Pantalla principal.
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

        //Personajes.
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
            CharacterListScreenAppMain(modifier = Modifier.fillMaxSize())
        }

        //Localizaciones.
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

        //Lista de episodios.
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
            EpisodesListAppMain() { episodeId ->
                navController.navigate(route = Routes.Episode_Single.name + "/$episodeId")
            }
        }

        //Episodio unico seleccionado.
        composable(
            route = Routes.Episode_Single.name + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    SlideDirection.End,
                    tween(1000)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    SlideDirection.Start,
                    tween(1000)
                )
            }
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id") ?: 0
            EpisodeSelectedResultScreen(id = id)
        }
    }
}
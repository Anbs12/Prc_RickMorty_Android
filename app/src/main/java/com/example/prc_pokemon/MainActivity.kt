package com.example.prc_pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prc_pokemon.ui.navigation.Routes
import com.example.prc_pokemon.ui.screens.CharactersListScreen.CharacterListScreenAppMain
import com.example.prc_pokemon.ui.screens.EpisodesListScreen.EpisodesListAppMain
import com.example.prc_pokemon.ui.screens.LocationsListScreen.LocationsAppMain
import com.example.prc_pokemon.ui.screens.mainScreen.MainApp
import com.example.prc_pokemon.ui.theme.Prc_PokemonTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Prc_PokemonTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Main.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Routes.Main.name) {
                            MainApp(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                onGoToScreen = { onSelectedIndex ->
                                    when (onSelectedIndex) {
                                        0 -> navController.navigate(route = Routes.Characters_Route.name)
                                        1 -> navController.navigate(route = Routes.Locations_Route.name)
                                        2 -> navController.navigate(route = Routes.Episodes_Route.name)
                                    }
                                }
                            )
                        }

                        composable(route = Routes.Characters_Route.name) {
                            CharacterListScreenAppMain()
                        }
                        composable(route = Routes.Locations_Route.name) {
                            LocationsAppMain()
                        }
                        composable(route = Routes.Episodes_Route.name) {
                            EpisodesListAppMain()
                        }
                    }

                }
            }
        }
    }
}
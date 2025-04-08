package com.example.prc_pokemon

import android.annotation.SuppressLint
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
import com.example.prc_pokemon.ui.screens.CharactersListScreen.CharacterListScreenApp
import com.example.prc_pokemon.ui.screens.mainScreen.MainApp
import com.example.prc_pokemon.ui.theme.Prc_PokemonTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("StateFlowValueCalledInComposition")
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
                                onGoCharactersScreen = {
                                    navController.navigate(route = Routes.Characters_Route.name)
                                }
                            )
                        }
                        composable(route = Routes.Characters_Route.name) {
                            CharacterListScreenApp()
                        }
                    }

                }
            }
        }
    }
}
package com.example.prc_pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.prc_pokemon.ui.navigation.NavigationAppMain
import com.example.prc_pokemon.ui.theme.Prc_PokemonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prc_PokemonTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavigationAppMain(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}
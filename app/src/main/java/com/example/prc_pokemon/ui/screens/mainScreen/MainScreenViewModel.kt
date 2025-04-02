package com.example.prc_pokemon.ui.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.TAGS.TAG_MAINS_VIEWMODEL
import com.example.prc_pokemon.data.network.ApiService
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

// Define los posibles estados de la UI para esta pantalla
data class UserScreenState(
    val isLoading: Boolean = false,
    val pokemons: String = "",
    val error: String? = null
)

class MainScreenViewModel() : ViewModel() {


    private val _state = MutableStateFlow(UserScreenState())
    val state: StateFlow<UserScreenState> = _state


    fun getData() {
        viewModelScope.launch {
            try {
                // Pone el estado en modo "cargando"
                _state.value = UserScreenState(isLoading = true)

                val pokemonListResult = RetrofitInstance.retrofitBuilder.getData()
                _state.value = UserScreenState(pokemons = pokemonListResult)
                if (pokemonListResult.isNotEmpty()) {
                    _state.value = UserScreenState(isLoading = false)
                    println(pokemonListResult)
                }
            } catch (e: Exception) {
                Log.e(TAG_MAINS_VIEWMODEL, e.message.toString())
                _state.value = UserScreenState(isLoading = false, error = e.message)
            }
        }
    }

}
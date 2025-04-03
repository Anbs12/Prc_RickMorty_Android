package com.example.prc_pokemon.ui.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.RickMortyModelList
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_MAINS_VIEWMODEL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Define los posibles estados de la UI para esta pantalla
data class UserScreenState(
    val isLoading: Boolean = false,
    val data: List<RickMortyModelList> = emptyList(),
    val error: String = ""
)

class MainScreenViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UserScreenState())
    val state: StateFlow<UserScreenState> = _state
    val retrofit = RetrofitInstance.retrofitBuilder

    init {
        getRickMortyData()
    }

    fun getRickMortyData() {
        viewModelScope.launch {
            try {
                _state.value = UserScreenState(isLoading = true)

                //Llamamos a la funcion.
                val result = retrofit.getRickMortyListData()
                val lista = arrayListOf(
                    RickMortyModelList(name = "Personajes", url = result.characters),
                    RickMortyModelList(name = "Localizaciones", url = result.locations),
                    RickMortyModelList(name = "Episodios", url = result.episodes)
                )

                delay(1000)

                //Actualizamos value.
                _state.value = UserScreenState(data = lista, isLoading = false)
            } catch (e: Exception) {
                Log.e(TAG_MAINS_VIEWMODEL, e.message.toString())
                _state.value = UserScreenState(isLoading = false, error = e.message.toString())
            }
        }
    }


}
package com.example.prc_pokemon.ui.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.PrincipalList
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_MAINS_VIEWMODEL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Define los posibles estados de la UI para esta pantalla
data class UserScreenState(
    val isLoading: Boolean = false,
    val data: List<PrincipalList> = emptyList(),
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
                _state.value = UserScreenState(isLoading = true) //State: Cargando...

                //Guarda datos en variable.
                val result = retrofit.getRickMortyListData()
                //Crea nueva lista.
                val lista = arrayListOf(
                    PrincipalList(name = "Personajes", url = result.personajes),
                    PrincipalList(name = "Localizaciones", url = result.locations),
                    PrincipalList(name = "Episodios", url = result.episodes)
                )
                //Actualiza valor: State. Inserta datos a "data".
                _state.value = UserScreenState(data = lista, isLoading = false)
                delay(1000)
            } catch (e: Exception) {
                Log.e(TAG_MAINS_VIEWMODEL, e.message.toString())
                //Devuelve mensaje error en State.
                _state.value = UserScreenState(isLoading = false, error = e.message.toString())
            }
        }
    }


}
package com.example.prc_pokemon.ui.screens.LocationsListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.SingleLocation
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_LOCATIONSSCREEN_VM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

/**Maneja el estado de la UI de la pantalla de la aplicacion.*/
sealed interface LocationsListScreenUIState {
    object Success : LocationsListScreenUIState
    object Loading : LocationsListScreenUIState
    object Error : LocationsListScreenUIState
}

class LocationsListScreenViewModel : ViewModel() {

    private val retrofit = RetrofitInstance.retrofitBuilder

    var uiState: LocationsListScreenUIState by mutableStateOf(LocationsListScreenUIState.Loading)

    private var nextLocationURL = ""
    private var _isNextLocationURLEmpty = MutableStateFlow(false)
    val isNextLocationURLEmpty: StateFlow<Boolean> = _isNextLocationURLEmpty

    private val _dinamicList = MutableStateFlow<MutableList<SingleLocation>>(mutableListOf())
    val dinamicList: StateFlow<MutableList<SingleLocation>> = _dinamicList

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            try {
                val data = retrofit.getLocations()
                //Almacena la primera Url.
                nextLocationURL = data.locationsInfo.next
                _dinamicList.value = data.results
                delay(1000)
                uiState = LocationsListScreenUIState.Success
            } catch (e: Exception) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            } catch (e: IOException) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            } catch (e: HttpException) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            }
        }
    }

    fun getNextLocations() {
        viewModelScope.launch {
            try {
                if (nextLocationURL != null) {
                    val data = retrofit.getNextLocations(nextLocationURL)
                    //Almacena la siguiente parte del Url (Hay hasta 7 maximo.)
                    nextLocationURL = data.locationsInfo.next
                    //Combina la lista anterior con la siguiente.
                    _dinamicList.value =
                        (_dinamicList.value + data.results) as MutableList<SingleLocation>
                } else {
                    _isNextLocationURLEmpty.value = true
                    println("LIMITE")
                }
                delay(1000)
                uiState = LocationsListScreenUIState.Success
            } catch (e: Exception) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            } catch (e: IOException) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            } catch (e: HttpException) {
                Log.e(TAG_LOCATIONSSCREEN_VM, "Error: ${e.message}")
                uiState = LocationsListScreenUIState.Error
            }
        }
    }

}
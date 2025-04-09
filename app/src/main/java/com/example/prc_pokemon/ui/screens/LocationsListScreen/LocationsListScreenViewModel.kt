package com.example.prc_pokemon.ui.screens.LocationsListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_LOCATIONSSCREEN_VM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface LocationsListScreenUIState {
    data class Success(val locations: Locations) : LocationsListScreenUIState
    object Loading : LocationsListScreenUIState
    object Error : LocationsListScreenUIState
}

class LocationsListScreenViewModel : ViewModel() {
    var uiState: LocationsListScreenUIState by mutableStateOf(LocationsListScreenUIState.Loading)

    private val retrofit = RetrofitInstance.retrofitBuilder

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            try {

                val data = retrofit.getLocations()
                delay(1000)
                uiState = LocationsListScreenUIState.Success(data)

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
package com.example.prc_pokemon.ui.screens.EpisodesListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.SingleEpisode
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_SINGLEEPSSCREEN_VM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface EpisodeSelectedScreenUIState {
    data class Success(val data: SingleEpisode) : EpisodeSelectedScreenUIState
    object Loading : EpisodeSelectedScreenUIState
    data class Error(val message: String) : EpisodeSelectedScreenUIState
}

class EpisodeSelectedScreenViewModel : ViewModel() {

    var uiState: EpisodeSelectedScreenUIState by mutableStateOf(EpisodeSelectedScreenUIState.Loading)

    private var retrofit = RetrofitInstance.retrofitBuilder

    /**Inicia la carga de datos y actualiza el uiState
     * @param id Id del episodio*/
    fun initData(id: Int) {
        viewModelScope.launch {
            try {
                val data = retrofit.getSingleEpisodeInfo(id)
                delay(1000)
                uiState = EpisodeSelectedScreenUIState.Success(data)
            } catch (e: Exception) {
                Log.e(TAG_SINGLEEPSSCREEN_VM, e.message.toString())
                uiState = EpisodeSelectedScreenUIState.Error(e.message.toString())
            } catch (e: IOException) {
                Log.e(TAG_SINGLEEPSSCREEN_VM, e.message.toString())
                uiState = EpisodeSelectedScreenUIState.Error(e.message.toString())
            } catch (e: HttpException) {
                Log.e(TAG_SINGLEEPSSCREEN_VM, e.message.toString())
                uiState = EpisodeSelectedScreenUIState.Error(e.message.toString())
            }
        }
    }

}
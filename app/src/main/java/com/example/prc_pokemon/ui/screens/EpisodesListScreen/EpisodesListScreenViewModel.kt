package com.example.prc_pokemon.ui.screens.EpisodesListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_EPISODESSCREEN_VM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface EpisodesListScreenUiState {
    data class Success(val episodes: Episodes) : EpisodesListScreenUiState
    object Loading : EpisodesListScreenUiState
    object Error : EpisodesListScreenUiState
}

class EpisodesListScreenViewModel : ViewModel() {
    var uiState: EpisodesListScreenUiState by mutableStateOf(EpisodesListScreenUiState.Loading)

    private val retrofit = RetrofitInstance.retrofitBuilder

    init {
        getEpisodes()
    }

    private fun getEpisodes() {
        viewModelScope.launch {
            try {
                val data = retrofit.getEpisodes()
                delay(1000)
                uiState = EpisodesListScreenUiState.Success(data)

            } catch (e: Exception) {
                Log.e(TAG_EPISODESSCREEN_VM, "Error: ${e.message}")
                uiState = EpisodesListScreenUiState.Error
            } catch (e: IOException) {
                Log.e(TAG_EPISODESSCREEN_VM, "Error: ${e.message}")
                uiState = EpisodesListScreenUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG_EPISODESSCREEN_VM, "Error: ${e.message}")
                uiState = EpisodesListScreenUiState.Error
            }
        }
    }
}
package com.example.prc_pokemon.ui.screens.CharactersListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.network.RetrofitInstance
import com.example.prc_pokemon.data.utils.TAGS.TAG_CHARACTERSREEN_VM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CharacterScreenUiState {
    data class Success(val charactersList: Characters) : CharacterScreenUiState
    object Loading : CharacterScreenUiState
    object Error : CharacterScreenUiState
}

class CharacterListScreenViewModel : ViewModel() {

    //Modo Kotlin Flow
    private var _uiState = MutableStateFlow(1)
    val uiState: StateFlow<Int> = _uiState

    var charactersUiState: CharacterScreenUiState by mutableStateOf(CharacterScreenUiState.Loading)

    private val retrofit = RetrofitInstance.retrofitBuilder

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val characters = retrofit.getCharactersList()
                delay(1000)
                charactersUiState = CharacterScreenUiState.Success(characters)
            } catch (e: IOException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: Exception) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            }
        }
    }

    fun getCharactersNextPage() {
        viewModelScope.launch {
            try {
                val characters = retrofit.getCharactersNextPage(_uiState.value++)
                charactersUiState = CharacterScreenUiState.Loading
                delay(1000)
                charactersUiState = CharacterScreenUiState.Success(characters)
            } catch (e: IOException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: Exception) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            }
        }
    }

    fun getCharactersPreviousPage() {
        viewModelScope.launch {
            try {
                val characters = retrofit.getCharactersPreviousPage(_uiState.value--)
                charactersUiState = CharacterScreenUiState.Loading
                delay(1000)
                charactersUiState = CharacterScreenUiState.Success(characters)
            } catch (e: IOException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            } catch (e: Exception) {
                Log.e(TAG_CHARACTERSREEN_VM, "Error: ${e.message}")
                charactersUiState = CharacterScreenUiState.Error
            }
        }
    }

}


/** The mutable State that stores the status of the most recent request
 * Distinta manera de aplicar State a una variable
var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
private set

marsUiState = MarsUiState.Loading
 */
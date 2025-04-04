package com.example.prc_pokemon.ui.screens.CharactersListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prc_pokemon.data.model.Characters
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

    private val _uiState = MutableStateFlow(CharacterScreenUiState.Loading)
    val uiState: StateFlow<CharacterScreenUiState> = _uiState

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {

            } catch (e: IOException) {
                CharacterScreenUiState.Error
            } catch (e: HttpException) {
                CharacterScreenUiState.Error
            } catch (e: Exception) {
                CharacterScreenUiState.Error
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
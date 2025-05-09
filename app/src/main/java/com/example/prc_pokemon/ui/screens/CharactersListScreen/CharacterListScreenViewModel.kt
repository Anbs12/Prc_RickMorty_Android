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

/**Maneja el estado de la UI de la pantalla de la aplicacion.*/
sealed interface CharacterScreenUiState {
    data class Success(val charactersList: Characters) : CharacterScreenUiState
    object Loading : CharacterScreenUiState
    object Error : CharacterScreenUiState
}

class CharacterListScreenViewModel : ViewModel() {

    /** Almacena url de paginacion siguiente.*/
    private var _nextUrlPage = MutableStateFlow("")

    /** Lee url de paginacion siguiente.*/
    val nextStatePage: StateFlow<String> = _nextUrlPage

    /** Almacena url de paginacion anterior.*/
    private var _prevUrlPage = MutableStateFlow("")

    /** Lee url de paginacion anterior.*/
    val previousStatePage: StateFlow<String> = _prevUrlPage

    /** Almacena numero de pagina actual.*/
    private var _nPage = MutableStateFlow(0)

    /** Lee numero de pagina actual.*/
    val nPage: StateFlow<Int> = _nPage

    /** Almacena si se ha utilizado el SearchBar en la Screen de los personajes.*/
    private var _isSearchBarUsed = MutableStateFlow(false)

    /** Lee si se ha utilizado el SearchBar en la Screen de los personajes..*/
    var isSearchBarUsed: StateFlow<Boolean> = _isSearchBarUsed

    /** Maneja el estado de la UI dentro del viewmodel.*/
    var charactersUiState: CharacterScreenUiState by mutableStateOf(CharacterScreenUiState.Loading)

    private val retrofit = RetrofitInstance.retrofitBuilder

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                charactersUiState = CharacterScreenUiState.Loading
                val characters = retrofit.getCharactersList()
                _nextUrlPage.value = characters.info.next
                _prevUrlPage.value = characters.info.prev
                _isSearchBarUsed.value = false
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
                charactersUiState = CharacterScreenUiState.Loading
                val characters = retrofit.getCharactersNextPage(_nextUrlPage.value)
                _nextUrlPage.value = characters.info.next
                _prevUrlPage.value = characters.info.prev
                _nPage.value++
                delay(500)
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
                charactersUiState = CharacterScreenUiState.Loading
                val characters = retrofit.getCharactersPreviousPage(_prevUrlPage.value)
                _nextUrlPage.value = characters.info.next
                _prevUrlPage.value = characters.info.prev
                _nPage.value--
                delay(500)
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

    fun getFilteredCharacter(name: String) {
        viewModelScope.launch {
            try {
                charactersUiState = CharacterScreenUiState.Loading
                delay(200)
                val characters = retrofit.getFilteredCharacter(name)
                _nPage.value = 0
                _isSearchBarUsed.value = true
                delay(500)
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

//Nota:
/** The mutable State that stores the status of the most recent request
 * Distinta manera de aplicar State a una variable
var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
private set

marsUiState = MarsUiState.Loading
 */
package com.sanjay.learning.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.learning.data.repositories.PokemonDetailsRepository
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonDetailsRepository: PokemonDetailsRepository,
    name: String
) : ViewModel() {
    private val _loadingLiveData = MutableLiveData<PokemonDetailsLoadingViewState>()
    val loadingLiveData: LiveData<PokemonDetailsLoadingViewState> = _loadingLiveData

    private val _liveData = MutableLiveData<PokemonDetailsViewState>()
    val liveData: LiveData<PokemonDetailsViewState> = _liveData

    init {
        getPokemonDetails(name)
    }

    private fun getPokemonDetails(name: String) {
        viewModelScope.launch {
            runCatching {
                _loadingLiveData.value = PokemonDetailsLoadingViewState.Loading
                pokemonDetailsRepository.getPokemonDetails(name)

            }.onSuccess { pokemonDetails ->
                _loadingLiveData.value = PokemonDetailsLoadingViewState.Loaded
                _liveData.value = PokemonDetailsViewState.PokemonDetailsSuccess(pokemonDetails)
            }.onFailure {
                _loadingLiveData.value = PokemonDetailsLoadingViewState.Error
                _liveData.value = PokemonDetailsViewState.PokemonDetailsError(it)
            }
        }
    }
}

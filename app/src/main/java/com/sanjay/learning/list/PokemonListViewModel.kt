package com.sanjay.learning.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.sanjay.learning.*
import com.sanjay.learning.data.repositories.PokemonRepository
import com.sanjay.learning.paging.PagerFactory

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository,
    pagerFactory: PagerFactory
) : ViewModel(),
    ViewEventListener {

    private val _singleViewState = SingleLiveData<PokemonSingleViewState>()
    val pokemonSingleViewState: LiveData<PokemonSingleViewState> = _singleViewState

    val pagingDataViewStates = pagerFactory.createOffsetPager(pokemonRepository::getPokemonList)
    .flow
    .cachedIn(viewModelScope)

    override fun onViewEvent(pokemonListViewEvent: PokemonListViewEvent) {
        when (pokemonListViewEvent) {
            is PokemonListViewEvent.OnPokemonClicked ->
                _singleViewState.value = PokemonSingleViewState.NavigateToDetails(
                    pokemonListViewEvent.name,
                    pokemonListViewEvent.imageUrl
                )
        }
    }
}

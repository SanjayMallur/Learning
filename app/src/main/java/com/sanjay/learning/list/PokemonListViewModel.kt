package com.sanjay.learning.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.sanjay.learning.*
import com.sanjay.learning.data.PokemonRepository
import com.sanjay.learning.paging.PagerFactory

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository,
    pagerFactory: PagerFactory
) : ViewModel(),
    ViewEventListener {

    private val _singleViewState = SingleLiveData<PokemonSingleViewState>()
    val pokemonSingleViewState: LiveData<PokemonSingleViewState> = _singleViewState


    //private val _pagingDataViewStates = MutableLiveData<PagingData<Pokemon>>()
//    val pagingDataViewStates: LiveData<PagingData<Pokemon>> = _pagingDataViewStates
    val pagingDataViewStates = pagerFactory.createOffsetPager(pokemonRepository::getPokemonList)
    .flow
    .cachedIn(viewModelScope)

//    init {
//        setupPaging()
//    }

//    private fun setupPaging() {
//        viewModelScope.launch {
//
//        }
//    }

    override fun onViewEvent(pokemonListViewEvent: PokeminListViewEvent) {
        when (pokemonListViewEvent) {
            is PokeminListViewEvent.OnPokemonClicked ->
                _singleViewState.value = PokemonSingleViewState.NavigateToDetails(
                    pokemonListViewEvent.name,
                    pokemonListViewEvent.imageUrl
                )
        }
    }
}

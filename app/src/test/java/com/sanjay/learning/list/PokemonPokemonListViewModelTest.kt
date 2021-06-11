package com.sanjay.learning.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sanjay.learning.CoroutineRule.TestCoroutineRule
import com.sanjay.learning.data.PokemonRepository
import com.sanjay.learning.paging.PagerFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class PokemonPokemonListViewModelTest {

    @Suppress("unused")
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Suppress("unused")
    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private val pokemonRepository: PokemonRepository = mock()

    @Test
    fun `Given pokemon list items when item is clicked the Navigate to details is called`() = coroutineTestRule.runBlockingTest {
        val viewModel = initViewModel()
        viewModel.onViewEvent(PokeminListViewEvent.OnPokemonClicked("Test", ""))
        Truth.assertThat(viewModel.pokemonSingleViewState.value)
            .isInstanceOf(PokemonSingleViewState.NavigateToDetails::class.java)
    }

    private fun initViewModel() = PokemonListViewModel(
        pokemonRepository = pokemonRepository,
        pagerFactory = PagerFactory()
    )
}

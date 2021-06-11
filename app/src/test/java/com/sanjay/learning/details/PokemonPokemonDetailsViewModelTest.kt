package com.sanjay.learning.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sanjay.learning.CoroutineRule.TestCoroutineRule
import com.sanjay.learning.data.PokemonDetails
import com.sanjay.learning.data.PokemonDetailsRepository
import com.sanjay.learning.livedata.getOrAwaitValue
import com.sanjay.learning.livedata.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PokemonPokemonDetailsViewModelTest {

    @Suppress("unused")
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Suppress("unused")
    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private val name = "Test"

    private val pokemonDetailsRepository: PokemonDetailsRepository = mock()

    private val pokemonDetails = PokemonDetails(1, name, 200, 100, 12, listOf())

    private fun initViewModel() = PokemonDetailsViewModel(
        pokemonDetailsRepository = pokemonDetailsRepository,
        name = name
    )

    @Test
    fun `Given pokemon name When view model is initialized then details is updated`() =
        coroutineTestRule.runBlockingTest {
            //When
            whenever(pokemonDetailsRepository.getPokemonDetails(name)).thenReturn(pokemonDetails)
            val viewModel = initViewModel()

            //Then
            assertEquals(
                viewModel.liveData.getOrAwaitValue(),
                PokemonDetailsViewState.PokemonDetailsSuccess(pokemonDetails)
            )
        }

    @Test
    fun `Given pokemon name When view model is initialized then error is Thrown`() =
        coroutineTestRule.runBlockingTest {
            //When
            val exception = RuntimeException()
            whenever(pokemonDetailsRepository.getPokemonDetails(name)).thenThrow(exception)
            val viewModel = initViewModel()

            //Then
            assertEquals(
                viewModel.liveData.getOrAwaitValue(),
                PokemonDetailsViewState.PokemonDetailsError(exception)
            )
        }

    @Test
    fun `Given pokemon name When view model is initialized Then loaded is shown`() =
        coroutineTestRule.runBlockingTest {
            //When
            whenever(pokemonDetailsRepository.getPokemonDetails(name)).thenReturn(pokemonDetails)
            val viewModel = initViewModel()

            //Then
            assertEquals(PokemonDetailsLoadingViewState.Loaded,
                viewModel.loadingLiveData.getOrAwaitValue()
            )
        }
}

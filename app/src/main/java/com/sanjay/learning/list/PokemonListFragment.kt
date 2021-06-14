package com.sanjay.learning.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanjay.learning.R
import com.sanjay.learning.databinding.PokemonListFragmentBinding
import com.sanjay.learning.paging.DefaultLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonListFragment : Fragment(R.layout.pokemon_list_fragment) {

    private var fragmentBinding: PokemonListFragmentBinding? = null

    private val viewModelPokemon: PokemonListViewModel by viewModel()

    private var isLargeDevice: Boolean = false

    private val pokemonAdapter by inject<PokemonAdapter> {
        parametersOf(this@PokemonListFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLargeDevice = resources.getBoolean(R.bool.isLargeWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PokemonListFragmentBinding.bind(view)
        fragmentBinding = binding
        setupList()
        observeSingleViewStates()
        observeViewStates()
    }

    private fun observeViewStates() {
        lifecycleScope.launch {
            viewModelPokemon.pagingDataViewStates.collect {
                pokemonAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setupList() {
        val stateAdapter = DefaultLoadStateAdapter { pokemonAdapter.retry() }

        pokemonAdapter.addLoadStateListener { loadState ->
            fragmentBinding?.recyclerView?.let { stateAdapter.handleInitialLoad(loadState, it) }
        }

        fragmentBinding?.recyclerView?.apply {
            adapter = ConcatAdapter(pokemonAdapter, stateAdapter)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeSingleViewStates() {
        viewModelPokemon.pokemonSingleViewState.observe(viewLifecycleOwner) { singleViewState ->
            when (singleViewState) {
                is PokemonSingleViewState.NavigateToDetails -> {
                    if (!isLargeDevice) {
                        findNavController().navigate(
                            PokemonListFragmentDirections.actionFragmentListToPokemonDetailsFragment(
                                singleViewState.name,
                                singleViewState.imageUrl
                            )
                        )
                    } else {
                        openPokemonDetails(
                            singleViewState.name,
                            singleViewState.imageUrl
                        )
                    }
                }
            }
        }
    }


    private fun openPokemonDetails(name: String, imageUrl: String) {
        val bundle = Bundle().apply {
            putString(NAME, name)
            putString(IMAGE_URL, imageUrl)
        }
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.detailContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val navOptions = NavOptions.Builder()
            // Pop all destinations off the back stack.
            .setPopUpTo(navController.graph.startDestination, true)
            .build()
        navController.navigate(R.id.detailFragment, bundle, navOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding?.recyclerView?.adapter = null
        fragmentBinding = null
    }

    companion object {
        private const val NAME = "name"
        private const val IMAGE_URL = "imageUrl"
    }
}

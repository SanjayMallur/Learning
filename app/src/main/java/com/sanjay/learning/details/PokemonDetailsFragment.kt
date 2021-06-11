package com.sanjay.learning.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sanjay.learning.R
import com.sanjay.learning.data.PokemonDetails
import com.sanjay.learning.databinding.PokemonDetailsFragmentBinding
import com.sanjay.learning.glide.ImageLoader
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetailsFragment : Fragment(R.layout.pokemon_details_fragment) {

    private var fragmentDetailsBinding: PokemonDetailsFragmentBinding? = null

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()

    private val imageLoader = ImageLoader.with(this)

    private val viewModel: PokemonDetailsViewModel by viewModel {
        parametersOf(navArgs.name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PokemonDetailsFragmentBinding.bind(view)
        fragmentDetailsBinding = binding
        observeViewStates()
        observeLoadingStates()
    }

    private fun observeLoadingStates() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonDetailsLoadingViewState.Loading -> {
                    showLoading()
                }
                is PokemonDetailsLoadingViewState.Loaded -> {
                    hideLoading()
                }
                is PokemonDetailsLoadingViewState.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun observeViewStates() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonDetailsViewState.PokemonDetailsSuccess -> {
                    setupDetails(state.pokemonDetails)
                }
                is PokemonDetailsViewState.PokemonDetailsError -> {
                    Toast.makeText(
                        this.context,
                        getString(R.string.error_occurred) + ": ${state.throwable}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupDetails(pokemonDetails: PokemonDetails) {
        fragmentDetailsBinding?.nameTextView?.text = getString(R.string.name)+ pokemonDetails.name
        fragmentDetailsBinding?.weightTextView?.text = getString(R.string.weight)+pokemonDetails.weight.toString()
        fragmentDetailsBinding?.heightTextView?.text = getString(R.string.height)+pokemonDetails.height.toString()
        fragmentDetailsBinding?.pokemonImageView?.let {
            imageLoader.load(navArgs.imageUrl).into(it)
        }
    }

    private fun showLoading(){
        fragmentDetailsBinding?.progressBar?.isGone = false
        fragmentDetailsBinding?.nameTextView?.isGone = true
        fragmentDetailsBinding?.weightTextView?.isGone = true
        fragmentDetailsBinding?.heightTextView?.isGone = true
        fragmentDetailsBinding?.pokemonImageView?.isGone = true
    }

    private fun hideLoading(){
        fragmentDetailsBinding?.progressBar?.isGone = true
        fragmentDetailsBinding?.nameTextView?.isGone = false
        fragmentDetailsBinding?.weightTextView?.isGone = false
        fragmentDetailsBinding?.heightTextView?.isGone = false
        fragmentDetailsBinding?.pokemonImageView?.isGone = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailsBinding = null
    }
}

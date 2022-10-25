package me.danielaguilar.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.danielaguilar.pokedex.databinding.FragmentPokemonDetailsBinding
import me.danielaguilar.pokedex.domain.PokemonInfo
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.presentation.viewmodel.PokemonDetailsViewModel
import me.danielaguilar.pokedex.util.ImageLoader
import me.danielaguilar.pokedex.util.extension.gone
import me.danielaguilar.pokedex.util.extension.visible

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonDetailsViewModel by viewModels()
    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokedexIndexViewState.Loading ->
                    showLoading()
                is PokedexIndexViewState.Error -> {
                    hideLoading()
                    showError(state.errorMessage)
                }
                is PokedexIndexViewState.Success -> {
                    setPokemonInfo(state.data)
                    hideLoading()
                    showPokemon()
                }
            }
        }
        arguments.let {
            val pokemonId = it?.getInt("pokemonId")
            viewModel.getPokemonInfo(pokemonId!!)
        }

    }

    private fun setPokemonInfo(data: PokemonInfo) {
        binding.pokemonName.text = data.name
        ImageLoader().loadImage(data.imageUrl, requireContext(), binding.pokemonDescriptionAvatar)
        binding.pokemonAttack.text = data.attacks.joinToString(", ") { a -> a.name }
        binding.pokemonType.text = data.types.joinToString(", ") { t -> t.name }
        binding.pokemonSkill.text = data.skills.joinToString(", ") { a -> a.name }
        binding.pokemonLocation.text = data.locations.joinToString(", ") { l -> l.name }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.pokemonInfo, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun hideLoading() {
        binding.loadingPokemon.gone()
    }

    private fun showLoading() {
        binding.loadingPokemon.visible()
        binding.pokemonInfo.gone()
    }

    private fun showPokemon() {
        binding.pokemonInfo.visible()
    }
}
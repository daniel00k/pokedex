package me.danielaguilar.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.danielaguilar.pokedex.R
import me.danielaguilar.pokedex.databinding.FragmentPokedexIndexBinding
import me.danielaguilar.pokedex.domain.PokemonSummary
import me.danielaguilar.pokedex.presentation.adapter.OnClickListener
import me.danielaguilar.pokedex.presentation.adapter.PokemonListAdapter
import me.danielaguilar.pokedex.presentation.uistate.PokedexIndexViewState
import me.danielaguilar.pokedex.presentation.viewmodel.PokedexIndexViewModel
import me.danielaguilar.pokedex.util.extension.gone
import me.danielaguilar.pokedex.util.extension.visible

@AndroidEntryPoint
class PokedexIndexFragment : Fragment(), OnClickListener {

    private lateinit var navigationController: NavController

    private val viewModel: PokedexIndexViewModel by viewModels()
    private var _binding: FragmentPokedexIndexBinding? = null
    private val binding get() = _binding!!

    private var pokemonSummaryList = listOf<PokemonSummary>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexIndexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        setAdapter(pokemonSummaryList)
        binding.pokemonsList.layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.span_count))
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokedexIndexViewState.Loading ->
                    showLoading()
                is PokedexIndexViewState.Error -> {
                    hideLoading()
                    showError(state.errorMessage)
                    showReloadButton()
                }
                is PokedexIndexViewState.Success -> {
                    hideLoading()
                    showPokemonList()
                    this.pokemonSummaryList = state.pokemonList
                    setAdapter(state.pokemonList)
                }

            }

        }
        viewModel.getPokemons()
    }

    override fun onDestroyView() {
        viewModel.setData(pokemonSummaryList)
        viewModel.setRecyclerState(binding.pokemonsList.layoutManager?.onSaveInstanceState()!!)
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter(pokemonSummaries: List<PokemonSummary>) {
        binding.pokemonsList.adapter =
            PokemonListAdapter(pokemonSummaries, requireContext(), this)
        viewModel.getRecyclerState()?.let {
            (binding.pokemonsList.layoutManager as GridLayoutManager).onRestoreInstanceState(it)
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.pokemonsList, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun hideLoading() {
        hideReloadButton()
        binding.loadingPokemons.gone()
    }

    private fun showLoading() {
        hideReloadButton()
        binding.loadingPokemons.visible()
        binding.pokemonsList.gone()
    }

    private fun showPokemonList() {
        hideReloadButton()
        binding.pokemonsList.visible()
    }

    private fun showReloadButton() {
        binding.reloadPokemons.visible()
    }

    private fun hideReloadButton() {
        binding.reloadPokemons.gone()
    }

    override fun onPokemonSelected(pokemonSummary: PokemonSummary) {
        TODO("Not yet implemented")
    }
}
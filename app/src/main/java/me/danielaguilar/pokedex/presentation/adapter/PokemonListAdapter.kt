package me.danielaguilar.pokedex.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.danielaguilar.pokedex.R
import me.danielaguilar.pokedex.domain.PokemonSummary
import me.danielaguilar.pokedex.presentation.viewholder.PokemonViewHolder
import me.danielaguilar.pokedex.util.ImageLoader

interface OnClickListener {
    fun onPokemonSelected(pokemonSummary: PokemonSummary)
}

class PokemonListAdapter(
    private val pokemons: List<PokemonSummary>,
    private val context: Context,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_list_item, parent, false)

        return PokemonViewHolder(view)

    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        ImageLoader().loadImage(pokemons[position].imageUrl, context, holder.avatar)
        holder.itemView.setOnClickListener {
            onClickListener.onPokemonSelected(pokemons[position])
        }
    }

    override fun getItemCount() = pokemons.size
}
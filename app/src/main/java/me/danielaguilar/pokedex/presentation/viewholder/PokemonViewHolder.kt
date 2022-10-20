package me.danielaguilar.pokedex.presentation.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import me.danielaguilar.pokedex.R

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val avatar: ImageView

    init {
        avatar = itemView.findViewById(R.id.pokemonAvatar)
    }

}
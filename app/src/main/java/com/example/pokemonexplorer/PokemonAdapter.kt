package com.example.pokemonexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Pokemon(
    val name: String,
    val height: Int,
    val weight: Int
)

class PokemonAdapter(
    private val pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.itemPokemonName)
        val pokemonInfo: TextView = itemView.findViewById(R.id.itemPokemonInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.pokemonName.text = pokemon.name
        holder.pokemonInfo.text =
            "Height: ${pokemon.height} | Weight: ${pokemon.weight}"
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}

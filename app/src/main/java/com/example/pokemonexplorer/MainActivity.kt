
package com.example.pokemonexplorer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonName: TextView
    private lateinit var pokemonInfo: TextView
    private lateinit var nextButton: Button
    private lateinit var pokemonRecyclerView: RecyclerView

    private var pokemonId = 1

    private val pokemonList = mutableListOf<Pokemon>()
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonName = findViewById(R.id.pokemonName)
        pokemonInfo = findViewById(R.id.pokemonInfo)
        nextButton = findViewById(R.id.nextButton)
        pokemonRecyclerView = findViewById(R.id.pokemonRecyclerView)

        pokemonRecyclerView.layoutManager =
            LinearLayoutManager(this)

        pokemonAdapter = PokemonAdapter(pokemonList)
        pokemonRecyclerView.adapter = pokemonAdapter

        getPokemon()

        nextButton.setOnClickListener {
            pokemonId++
            getPokemon()
        }
    }

    private fun getPokemon() {

        val client = AsyncHttpClient()
        val currentPokemonId = pokemonId

        val url =
            "https://pokeapi.co/api/v2/pokemon/$currentPokemonId"

        client.get(url, object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Headers?,
                json: JSON
            ) {

                val pokemon = json.jsonObject

                val name = pokemon.getString("name")
                val height = pokemon.getInt("height")
                val weight = pokemon.getInt("weight")

                runOnUiThread {

                    val displayName =
                        name.replaceFirstChar { it.uppercase() }

                    pokemonName.text = displayName

                    pokemonInfo.text =
                        "Height: $height\nWeight: $weight\nPokémon #$currentPokemonId"

                    pokemonList.add(
                        Pokemon(displayName, height, weight)
                    )

                    pokemonAdapter.notifyItemInserted(
                        pokemonList.size - 1
                    )
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {

                runOnUiThread {

                    pokemonName.text = "Error: $statusCode"

                    pokemonInfo.text =
                        response
                            ?: throwable?.message
                                    ?: "Unknown error"
                }
            }
        })
    }
}


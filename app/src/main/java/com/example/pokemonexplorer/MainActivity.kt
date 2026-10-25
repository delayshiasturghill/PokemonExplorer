package com.example.pokemonexplorer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonName: TextView
    private lateinit var pokemonInfo: TextView
    private lateinit var nextButton: Button

    private var pokemonId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonName = findViewById(R.id.pokemonName)
        pokemonInfo = findViewById(R.id.pokemonInfo)
        nextButton = findViewById(R.id.nextButton)

        getPokemon()

        nextButton.setOnClickListener {
            pokemonId++
            getPokemon()
        }
    }

    private fun getPokemon() {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon/$pokemonId"

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

                pokemonName.text = name.replaceFirstChar { it.uppercase() }

                pokemonInfo.text =
                    "Height: $height\nWeight: $weight\nPokémon #$pokemonId"
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                pokemonName.text = "Error loading Pokémon"
            }
        })
    }
}

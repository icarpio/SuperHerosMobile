package com.example.superherosmobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superherosmobile.R
import com.example.superherosmobile.activities.DetailActivity.Companion.EXTRA_ID
import com.example.superherosmobile.adapters.SuperHeroAdapter
import com.example.superherosmobile.data.SuperHeroApiService
import com.example.superherosmobile.databinding.ActivityMainBinding
import com.example.superherosmobile.utils.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SuperHeroAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navergar desde Main a Detail
        adapter = SuperHeroAdapter { id -> navigatetoDetail(id) }

        binding.recyclerView.adapter = adapter
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_activity_main, menu)

        val searchViewItem = menu.findItem(R.id.action_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Log.i("MENU", "He hecho click en el menu de busqueda")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun searchByName(query: String) {
        // Llamada en hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperHeroApiService::class.java)
                val result = apiService.findSuperheroesByName(query)
                // Llamada en hilo principal. En este hilo es donde se pueden gestionar los activities
                runOnUiThread {
                    if (result.results.isNullOrEmpty()) {
                        // Mostrar mensaje de que no se encontraron resultados
                        Toast.makeText(
                            this@MainActivity,
                            "No se encontraron resultados para '$query'",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Actualizar los datos del adaptador
                        adapter.updateData(result.results)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Error al realizar la búsqueda",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    //Navergar desde Main a Detail
    private fun navigatetoDetail(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }

}




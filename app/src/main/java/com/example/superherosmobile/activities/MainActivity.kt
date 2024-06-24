package com.example.superherosmobile.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherosmobile.R
import com.example.superherosmobile.adapters.SuperHeroAdapter
import com.example.superherosmobile.data.SuperHeroApiService
import com.example.superherosmobile.data.SuperHeroListResponse
import com.example.superherosmobile.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var adapter: SuperHeroAdapter
    lateinit var superheroList:List<SuperHeroListResponse>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SuperHeroAdapter()
        binding.recyclerView.adapter = adapter
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        //getSuperHeroByid("45")
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


    private fun searchByName(query: String){
        // Llamada en hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperHeroApiService::class.java)
                val result = apiService.findSuperheroesByName(query)
                runOnUiThread{
                    if (result.results.isNullOrEmpty()) {
                        // Mostrar mensaje de que no se encontraron resultados
                        Toast.makeText(this@MainActivity, "No se encontraron resultados para '$query'", Toast.LENGTH_SHORT).show()
                    } else {
                        // Actualizar los datos del adaptador
                        adapter.updateData(result.results)
                        Log.i("HTTP", "${result.results}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail = getRetrofit().create(SuperHeroApiService::class.java).getSuperHeroByid(id)

            if(superheroDetail.body() != null){
                runOnUiThread { Log.i("Hero Details", superheroDetail.body().toString()) }
            }
        }
    }




    // Paste your API Token in here:
    companion object {
        const val API_TOKEN = "31481a8cb409fd18a491ca3f03dc4ce7"
    }
    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/

        //31481a8cb409fd18a491ca3f03dc4ce7
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/$API_TOKEN/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    }




package com.example.superherosmobile.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.superherosmobile.R
import com.example.superherosmobile.data.PowerStatsResponse
import com.example.superherosmobile.data.SuperHeroApiService
import com.example.superherosmobile.data.SuperHeroDetailResponse
import com.example.superherosmobile.databinding.ActivityDetailBinding
import com.example.superherosmobile.utils.getRetrofit
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID = "extra-id"
    }
    private lateinit var binding:ActivityDetailBinding

    private lateinit var superhero:SuperHeroDetailResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        Log.i("HEROE ID",id)
        getSuperheroInformation(id)
    }
    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val superheroDetail = getRetrofit().create(SuperHeroApiService::class.java).getSuperHeroByid(id)
                if(superheroDetail.body() != null){
                    runOnUiThread {
                        createUI(superheroDetail.body()!!)

                    }
                }else {
                    Toast.makeText(this@DetailActivity, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@DetailActivity, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createUI(superhero:SuperHeroDetailResponse){
            Picasso.get().load(superhero.image.url).into(binding.shImageView)
            binding.shBiographyTextView.text = superhero.biography.fullname
            binding.shPublisherTextView.text = superhero.biography.publisher
            // Reemplazar comas y punto y coma por saltos de línea en la cadena para obtener el texto formateado
            val formattedConnections = superhero.connections.groupaffiliation
                .replace(", ", "\n")
                .replace("; ", "\n")

            showStats(superhero.powerstats)

        if(superhero.connections.groupaffiliation == "-"){
            binding.connectionsTextView.text = "NO Connections"
        }else{
            binding.connectionsTextView.text = formattedConnections
        }
        supportActionBar?.setTitle(superhero.name)
        loadPngImage(superhero.biography.publisher,)

    }

    //Actualiza la altura de una vista (view) en tu interfaz de usuario.
    // La nueva altura se basa en el valor pasado como una cadena (stat),
    // que se convierte en píxeles y luego se ajusta a densidad independiente de píxeles (dp).
    private fun updateHeight(view: View, stat: Int) {
        val params = view.layoutParams //Parametros de la vista o componente
        params.height = pixelesToDp(stat.toFloat())
        view.layoutParams = params
    }
    private fun showStats(powerStats: PowerStatsResponse){
        updateHeight(binding.viewStrength,powerStats.strength)
        updateHeight(binding.viewIntelligence,powerStats.intelligence)
        updateHeight(binding.viewCombat,powerStats.combat)
        updateHeight(binding.viewPower,powerStats.power)
        updateHeight(binding.viewSpeed,powerStats.speed)
        updateHeight(binding.viewDurability,powerStats.durability)
    }

    // convierte un valor en píxeles (px) a dp (densidad independiente de píxeles),
    // lo cual es útil para asegurar que las dimensiones se vean correctas en diferentes tamaños de pantalla y densidades de píxeles.
    private fun pixelesToDp(pixel: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, resources.displayMetrics).roundToInt()
    }

    private fun loadPngImage(value: String) {
        if (value == "DC Comics") {
            binding.shPublisherTextView.visibility = View.GONE
            binding.publisher.visibility = View.GONE
            binding.namePublisherTextView.visibility = View.VISIBLE
            binding.namePublisherTextView.setImageResource(R.drawable.dc)
        } else if(value == "Marvel Comics") {
            binding.namePublisherTextView.visibility = View.VISIBLE
            binding.publisher.visibility = View.GONE
            binding.namePublisherTextView.setImageResource(R.drawable.marvel)
            binding.shPublisherTextView.visibility = View.GONE
        } else {
            binding.namePublisherTextView.visibility = View.GONE
        }
    }






}
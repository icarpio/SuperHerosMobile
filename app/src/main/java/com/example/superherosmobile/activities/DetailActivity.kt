package com.example.superherosmobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
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
                        Log.i("Hero Details", superheroDetail.body().toString())
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
        binding.shNameTextView.text = superhero.name
        binding.shBiographyTextView.text = superhero.biography.fullname
        binding.shPublisherTextView.text = superhero.biography.publisher

        // Reemplazar comas y punto y coma por saltos de línea en la cadena para obtener el texto formateado
        val formattedConnections = superhero.connections.groupaffiliation
            .replace(", ", "\n")
            .replace("; ", "\n")
        binding.connectionsTextView.text = formattedConnections
    }

    //Actualiza la altura de una vista (view) en tu interfaz de usuario.
    // La nueva altura se basa en el valor pasado como una cadena (stat),
    // que se convierte en píxeles y luego se ajusta a densidad independiente de píxeles (dp).
    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        params.height = pixelesToDp(stat.toFloat())
        view.layoutParams = params
    }

    // convierte un valor en píxeles (px) a dp (densidad independiente de píxeles),
    // lo cual es útil para asegurar que las dimensiones se vean correctas en diferentes tamaños de pantalla y densidades de píxeles.
    private fun pixelesToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }




}
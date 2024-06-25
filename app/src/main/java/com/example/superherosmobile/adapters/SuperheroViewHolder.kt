package com.example.superherosmobile.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.superherosmobile.data.SuperHeroDetailResponse
import com.example.superherosmobile.data.SuperHeroListResponse
import com.example.superherosmobile.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(val binding:ItemSuperheroBinding):RecyclerView.ViewHolder(binding.root) {

    fun render(superhero: SuperHeroDetailResponse, onItemSelected: (String) ->Unit){
        binding.nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)

        //Pasa el id del superheroe al Adapter
        binding.root.setOnClickListener {
            onItemSelected(superhero.id)
        }
    }

}
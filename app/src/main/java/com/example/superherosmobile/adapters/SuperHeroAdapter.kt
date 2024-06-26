package com.example.superherosmobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.superherosmobile.R
import com.example.superherosmobile.data.SuperHeroDetailResponse
import com.example.superherosmobile.data.SuperHeroListResponse
import com.example.superherosmobile.databinding.ItemSuperheroBinding

class SuperHeroAdapter (
    private var dataset:List<SuperHeroDetailResponse> = emptyList(),
    //Navergar desde Main a Detail
    private val onItemSelected: (String) -> Unit):

    RecyclerView.Adapter<SuperheroViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context))
        return SuperheroViewHolder(binding)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
       holder.render(dataset[position],onItemSelected)
    }

    fun updateData(dataSet:List<SuperHeroDetailResponse>) {
        this.dataset = dataSet
        notifyDataSetChanged()
    }

}
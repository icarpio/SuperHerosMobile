package com.example.superherosmobile.data

import com.google.gson.annotations.SerializedName

data class SuperHeroListResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results-for") val resultsFor: String,
    @SerializedName("results") val results: List<SuperHeroResponse>
)

// Data class to represent an individual superhero item in the response
data class SuperheroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superheroImage: SuperheroImageResponse
)

// Data class to represent the image information of a superhero
data class SuperheroImageResponse(@SerializedName("url") val url: String){
}

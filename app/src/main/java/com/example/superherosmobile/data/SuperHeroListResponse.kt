package com.example.superherosmobile.data

import com.google.gson.annotations.SerializedName

data class SuperHeroListResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results-for") val resultsFor: String,
    @SerializedName("results") val results: List<SuperHeroResponse>
) {
}

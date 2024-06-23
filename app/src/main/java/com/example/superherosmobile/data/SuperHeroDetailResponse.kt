package com.example.superherosmobile.data

import com.google.gson.annotations.SerializedName


// Data class to represent the detailed response for a superhero
data class SuperHeroDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val image: SuperheroImageDetailResponse,
    @SerializedName("biography") val biography: SuperheroBiographyResponse,
    @SerializedName("connections") val connections: SuperheroConnectionsResponse
)
// Data class to represent the power statistics of a superhero
data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String,
)

// Data class to represent the image information of a superhero in detail
data class SuperheroImageDetailResponse(@SerializedName("url") val url: String)

// Data class to represent the biography information of a superhero
data class SuperheroBiographyResponse(
    @SerializedName("full-name") val fullname: String,
    @SerializedName("publisher") val publisher: String
)

// Data class to represent the connections information of a superhero
data class SuperheroConnectionsResponse(
    @SerializedName("group-affiliation") val groupaffiliation: String
)

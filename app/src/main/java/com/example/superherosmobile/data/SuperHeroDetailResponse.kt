package com.example.superherosmobile.data


import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.NumberFormatException


// Data class to represent the detailed response for a superhero
data class SuperHeroDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("place-of-birth") val placeBirth: String,
    @SerializedName("first-appearance") val firstAppearance: String,
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val image: SuperheroImageDetailResponse,
    @SerializedName("biography") val biography: SuperheroBiographyResponse,
    @SerializedName("connections") val connections: SuperheroConnectionsResponse
)
// Data class to represent the power statistics of a superhero
data class PowerStatsResponse(
    @JsonAdapter(IntegerAdapter::class) @SerializedName("intelligence") val intelligence: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("strength") val strength: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("speed") val speed: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("durability") val durability: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("power") val power: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("combat") val combat: Int
){
    fun getIntelligence():Float{
        return try {
            intelligence.toFloat()
        } catch (e:NumberFormatException){
            0.0F
        }
    }
}

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





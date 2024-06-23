package com.example.superherosmobile.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

data class SuperHeroResponse (
    @SerializedName("name") val name: String
) {
}
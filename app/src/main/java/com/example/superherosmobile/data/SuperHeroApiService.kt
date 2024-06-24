package com.example.superherosmobile.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperHeroListResponse
    @GET("{id}")
    suspend fun getSuperHeroByid(@Path("id") query: String) : Response<SuperHeroDetailResponse>
}
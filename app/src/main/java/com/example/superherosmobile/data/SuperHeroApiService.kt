package com.example.superherosmobile.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperHeroListResponse
    @GET("{id}")
    suspend fun getSuperHeroByid(@Path("id") query: String) : Response<SuperHeroDetailResponse>

    /*
    @Path se utiliza para sustituir partes de la URL en las solicitudes.
    Esto es útil cuando necesitas enviar parámetros como parte de la ruta de la URL.

    @Query se utiliza para añadir parámetros de consulta a la URL.
    Los parámetros de consulta se añaden después del signo ? en la URL y pueden ser múltiples, separados por &.
     */
}
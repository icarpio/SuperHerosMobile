package com.example.superherosmobile.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    fun getRetrofit(): Retrofit {
        val API_TOKEN = "31481a8cb409fd18a491ca3f03dc4ce7"
        //31481a8cb409fd18a491ca3f03dc4ce7
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/${API_TOKEN}/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


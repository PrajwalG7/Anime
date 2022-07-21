package com.example.anime

import retrofit2.Call
import retrofit2.http.GET

interface AnimeAPI {

    @GET("anime")
    fun getAnime(): Call<MutableList<String>>

}
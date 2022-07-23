package com.example.anime

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeAPI {

    @GET("anime")
    fun getAnime(): Call<MutableList<String>>

    @GET("anime")
    fun getCharacter(@Query("title")title:String,@Query("page")pageNo:Int): Call<MutableList<AnimeCharacterList>>

}
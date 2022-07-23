package com.example.anime


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val baseUrl = "https://animechan.vercel.app/api/available/"
    const val paginationUrl="https://animechan.vercel.app/api/quotes/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getPaginationInstance():Retrofit {
        return Retrofit.Builder().baseUrl(paginationUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}
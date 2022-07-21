package com.example.anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AnimeTitles : AppCompatActivity() {

    lateinit var anime_api:AnimeAPI
    lateinit var anime_list:Call<MutableList<String>>
    lateinit var recyclerViewAnime: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_titles)

        anime_api = RetrofitHelper.getInstance()
            .create(AnimeAPI::class.java)

        recyclerViewAnime=findViewById(R.id.anime_titles_rv)

        anime_list=anime_api.getAnime()
        anime_list.enqueue(object: Callback<MutableList<String>>{
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {

               val animeList= response.body()?.sorted()?.toMutableList()
                animeList?.removeAt(0)

                try {
                    recyclerViewAnime.adapter=AnimeAdapter(animeList!!)
                }catch (e:Exception){
                    Toast.makeText(applicationContext, "Something went wrong make.", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Toast.makeText(this@AnimeTitles, "Make sure you have active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })




    }
}
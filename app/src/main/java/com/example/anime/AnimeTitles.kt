package com.example.anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeTitles : AppCompatActivity() {

    lateinit var anime_api:AnimeAPI
    lateinit var anime_list:Call<List<String>>
    lateinit var recyclerViewAnime: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_titles)

        anime_api = RetrofitHelper.getInstance()
            .create(AnimeAPI::class.java)

        recyclerViewAnime=findViewById(R.id.anime_titles_rv)

        anime_list=anime_api.getAnime()
        anime_list.enqueue(object: Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
//                Log.d("onResponse",response.body().toString())
//                Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_SHORT).show()

//                val sizeOfResponse= response.body()?.size
              //  Toast.makeText(applicationContext, sizeOfResponse.toString(), Toast.LENGTH_SHORT).show()
                  recyclerViewAnime.adapter= response.body()?.let { AnimeAdapter(it.toList()) }

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
               //code to be executed onFailure
            }

        })




    }
}
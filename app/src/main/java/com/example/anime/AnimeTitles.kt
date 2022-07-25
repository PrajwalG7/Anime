package com.example.anime

import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.fonts.Font
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnimeTitles : AppCompatActivity() {

    lateinit var anime_api:AnimeAPI
    lateinit var anime_list:Call<MutableList<String>>
    lateinit var recyclerViewAnime: RecyclerView
    lateinit var searchView: SearchView
    lateinit var textViewChoose: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_titles)
        searchView=findViewById(R.id.search_anime)

        val typeface = ResourcesCompat.getFont(this.applicationContext, R.font.itim)
        searchView.setTypeFace(typeface)

        textViewChoose=findViewById(R.id.choose_anime_title)

        var animeList:MutableList<String>?=null

        anime_api = RetrofitHelper.getInstance()
            .create(AnimeAPI::class.java)

        recyclerViewAnime=findViewById(R.id.anime_titles_rv)

        anime_list=anime_api.getAnime()
        anime_list.enqueue(object: Callback<MutableList<String>>{
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {

               animeList= response.body()?.sorted()?.toMutableList()
                animeList?.removeAt(0)
                animeList?.add("naruto")

                try {
                    recyclerViewAnime.adapter=AnimeAdapter(applicationContext,animeList!!)
                }catch (e:Exception){
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Toast.makeText(this@AnimeTitles, "Make sure you have an active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

              return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                textViewChoose.visibility=View.INVISIBLE

                if(newText!=""&& newText!=null) {
                    try {
                        for (i in animeList!!) {
                            if (i.lowercase().contains(newText.lowercase())) {
                                recyclerViewAnime.adapter = AnimeAdapter(applicationContext,listOf(i))
                            }
                        }
                    }catch (e:Exception){
                    }
                }else{
                    try {
                        recyclerViewAnime.adapter = AnimeAdapter(applicationContext,animeList!!)
                    }catch (e:Exception){
                    }
                }
                return false
            }

        })

        searchView.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                textViewChoose.visibility=View.VISIBLE
                 return false
            }

        })



    }
    fun SearchView.setTypeFace(typeface: Typeface?) {
        val id = context.resources.getIdentifier("android:id/search_src_text", null, null)
        val searchText = searchView.findViewById(id) as TextView
        searchText.typeface = typeface
    }


}
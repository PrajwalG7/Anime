package com.example.anime

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.LinkedHashSet
import kotlin.math.log

class AnimeCharacters : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var searchView:SearchView
    lateinit var anime_api:AnimeAPI
    lateinit var anime_character_list:Call<MutableList<AnimeCharacterList>>
    val anime_character_page_list: MutableList<String> = mutableListOf()
    var anime_character_page_list_filtered: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_characters)

        recyclerView=findViewById(R.id.anime_characters_rv)
        textView=findViewById(R.id.choose_anime_character)
        searchView=findViewById(R.id.search_anime)
        val typeface = ResourcesCompat.getFont(this.applicationContext, R.font.itim)
        searchView.setTypeFace(typeface)


        val anime: String? = intent.getStringExtra("anime")
        textView.text=anime

        anime_api = RetrofitHelper.getPaginationInstance()
            .create(AnimeAPI::class.java)

        //surround with try catch
        anime_character_list=anime_api.getCharacter(anime!!,0)

        anime_character_list.enqueue(object: Callback<MutableList<AnimeCharacterList>>{
            override fun onResponse(
                call: Call<MutableList<AnimeCharacterList>>,
                response: Response<MutableList<AnimeCharacterList>>
            ) {

                val res=response.body()
                for (i in res?.indices!!){

                        anime_character_page_list.add(res[i].character)

                }
                anime_character_page_list_filtered= LinkedHashSet(anime_character_page_list).toMutableList()
                recyclerView.adapter=AnimeCharacterAdapter(applicationContext,anime_character_page_list_filtered)


            }

            override fun onFailure(call: Call<MutableList<AnimeCharacterList>>, t: Throwable) {
                Toast.makeText(this@AnimeCharacters, "Make sure you have an active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })





        //searchview
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                textView.visibility= View.INVISIBLE

                return false
            }

        })

        searchView.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                textView.visibility= View.VISIBLE
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
package com.example.anime

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.LinkedHashSet

class AnimeCharacters : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var searchView:SearchView
    lateinit var anime_api:AnimeAPI
    lateinit var anime_character_list:Call<MutableList<AnimeCharacterList>>
    val anime_character_page_list: MutableList<String> = mutableListOf()
    var anime_character_page_list_filtered: MutableList<String> = mutableListOf()
    var anime:String=""
    var  pageNo:Int=0
    var loadMoreData:Boolean=true
    lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_characters)

        recyclerView=findViewById(R.id.anime_characters_rv)
        textView=findViewById(R.id.choose_anime_character)
        searchView=findViewById(R.id.search_anime)
        progressBar=findViewById(R.id.progress_circular_character)
        val typeface = ResourcesCompat.getFont(this.applicationContext, R.font.itim)
        searchView.setTypeFace(typeface)

        anime = intent.getStringExtra("anime").toString()
        textView.text=anime

        anime_api = RetrofitHelper.getPaginationInstance()
            .create(AnimeAPI::class.java)

        callEnqueue()

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                textView.visibility= View.INVISIBLE
                if(newText!=""&& newText!=null) {
                    try {
                        for (i in anime_character_page_list) {
                            if (i.lowercase().contains(newText.lowercase())) {
                                recyclerView.adapter = AnimeCharacterAdapter(applicationContext,listOf(i),anime)
                            }
                        }
                    }catch (e:Exception){
                    }
                }else{
                    try {
                        recyclerView.adapter = AnimeCharacterAdapter(applicationContext,anime_character_page_list_filtered,anime)
                    }catch (e:Exception){
                    }
                }
                return false
            }

        })

        searchView.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                textView.visibility= View.VISIBLE
                return false
            }

        })



        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(loadMoreData) {
                    pageNo++
                    if (!recyclerView.canScrollVertically(1)) {
                        progressBar.visibility=View.VISIBLE
                        callEnqueuePaging()
                    }else{
                        progressBar.visibility=View.GONE
                    }
                }

            }
        })

    }

    fun SearchView.setTypeFace(typeface: Typeface?) {
        val id = context.resources.getIdentifier("android:id/search_src_text", null, null)
        val searchText = searchView.findViewById(id) as TextView
        searchText.typeface = typeface
    }

    fun callEnqueue(){
        anime_character_list=anime_api.getCharacter(anime,pageNo)

        anime_character_list.enqueue(object: Callback<MutableList<AnimeCharacterList>>{
            override fun onResponse(
                call: Call<MutableList<AnimeCharacterList>>,
                response: Response<MutableList<AnimeCharacterList>>
            ) {
                if(response.body()==null){
                    loadMoreData=false
                    progressBar.visibility=View.GONE
                }else {
                    val res = response.body()
                    for (i in res?.indices!!) {

                        anime_character_page_list.add(res[i].character)

                    }
                    anime_character_page_list_filtered =
                        LinkedHashSet(anime_character_page_list).toMutableList()
                    recyclerView.adapter = AnimeCharacterAdapter(
                        applicationContext,
                        anime_character_page_list_filtered,
                        anime
                    )
                }
            }

            override fun onFailure(call: Call<MutableList<AnimeCharacterList>>, t: Throwable) {
                Toast.makeText(this@AnimeCharacters, "Make sure you have an active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun callEnqueuePaging(){
        anime_character_list=anime_api.getCharacter(anime,pageNo)

        anime_character_list.enqueue(object: Callback<MutableList<AnimeCharacterList>>{
            override fun onResponse(
                call: Call<MutableList<AnimeCharacterList>>,
                response: Response<MutableList<AnimeCharacterList>>
            ) {
                if(response.body()==null){
                    loadMoreData=false
                    progressBar.visibility=View.GONE
                }else {
                    val res = response.body()
                    for (i in res?.indices!!) {

                        anime_character_page_list.add(res[i].character)

                    }
                    anime_character_page_list_filtered =
                        LinkedHashSet(anime_character_page_list).toMutableList()
                    recyclerView.adapter = AnimeCharacterAdapter(
                        applicationContext,
                        anime_character_page_list_filtered,
                        anime
                    )
                    recyclerView.scrollToPosition(anime_character_page_list_filtered.size-1)
                }
            }

            override fun onFailure(call: Call<MutableList<AnimeCharacterList>>, t: Throwable) {
                Toast.makeText(this@AnimeCharacters, "Make sure you have an active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}

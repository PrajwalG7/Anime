package com.example.anime

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class AnimeCharacterQuotes : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var textView: TextView

    lateinit var searchView: SearchView
    lateinit var anime_api:AnimeAPI
    lateinit var anime_character_list: Call<MutableList<AnimeCharacterList>>
    val anime_character_page_list: MutableList<String> = mutableListOf()
    var anime_character_page_list_filtered: MutableList<String> = mutableListOf()

    var anime:String=""
    var anime_character:String=""
    var pageNo:Int=0
    var loadMoreData:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_character_quotes)

        recyclerView=findViewById(R.id.anime_quotes_rv)
        textView=findViewById(R.id.choose_anime_quotes)
        searchView=findViewById(R.id.search_quote)

        val typeface = ResourcesCompat.getFont(this.applicationContext, R.font.itim)
        searchView.setTypeFace(typeface)

        anime = intent.getStringExtra("anime").toString()
        anime_character=intent.getStringExtra("anime_character").toString()
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
                                recyclerView.adapter = AnimeCharacterQuotesAdapter(applicationContext,listOf(i),anime)
                            }
                        }
                    }catch (e:Exception){
                    }
                }else{
                    try {
                        recyclerView.adapter = AnimeCharacterQuotesAdapter(applicationContext,anime_character_page_list_filtered,anime)
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
                       // progressBar.visibility=View.VISIBLE
                        callEnqueue()


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
        anime_character_list=anime_api.getCharacterQuote(anime_character,pageNo)

        anime_character_list.enqueue(object: Callback<MutableList<AnimeCharacterList>> {
            override fun onResponse(
                call: Call<MutableList<AnimeCharacterList>>,
                response: Response<MutableList<AnimeCharacterList>>
            ) {
                if(response.body()==null){
                   loadMoreData=false
                   // progressBar.visibility= View.GONE
                }else {
                    val res = response.body()
                    for (i in res?.indices!!) {

                        anime_character_page_list.add(res[i].quote)

                    }
                    anime_character_page_list_filtered =
                        LinkedHashSet(anime_character_page_list).toMutableList()
                    recyclerView.adapter = AnimeCharacterQuotesAdapter(
                        applicationContext,
                        anime_character_page_list_filtered,
                        anime
                    )

                }
            }

            override fun onFailure(call: Call<MutableList<AnimeCharacterList>>, t: Throwable) {
                Toast.makeText(this@AnimeCharacterQuotes, "Make sure you have an active Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
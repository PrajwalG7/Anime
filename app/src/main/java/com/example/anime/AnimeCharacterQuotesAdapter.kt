package com.example.anime

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AnimeCharacterQuotesAdapter(var mContext: Context, private val animeList:  List<String>,var anime:String ) : RecyclerView.Adapter<AnimeCharacterQuotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_quotes_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.animeTitle.text = animeList[position]

    }


    override fun getItemCount(): Int {
        return animeList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val animeTitle= itemView.findViewById<TextView>(R.id.anime_titles)
//        init {
//            itemView.setOnClickListener {
//                Toast.makeText(mContext, animeList[adapterPosition], Toast.LENGTH_SHORT).show()
//                mContext.startActivity(Intent(mContext,AnimeCharacterQuotes::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("anime_character",animeList[adapterPosition]).putExtra("anime",anime))
//            }
//        }

    }

}
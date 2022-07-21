package com.example.anime


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimeAdapter(private val animeList:  List<String> ) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_titles_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.animeTitle.text = animeList[position]


    }


    override fun getItemCount(): Int {
        return animeList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val animeTitle= itemView.findViewById<TextView>(R.id.anime_titles)

    }
}
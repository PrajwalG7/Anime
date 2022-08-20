package com.example.anime

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class AnimeCharacterQuotesAdapter(var mContext: Context, private val animeList:  List<String>,var anime:String ,var anime_character:String) : RecyclerView.Adapter<AnimeCharacterQuotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_quotes_card, parent, false)
        return ViewHolder(view)
    }



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.animeTitle.text = animeList[position]
        holder.animeCharacter.text= "~ $anime_character"
    }


    override fun getItemCount(): Int {
        return animeList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val animeTitle= itemView.findViewById<TextView>(R.id.anime_titles)
        val animeCharacter= itemView.findViewById<TextView>(R.id.anime_character)
        val shareContent=itemView.findViewById<ImageView>(R.id.share)
//        init {
//            itemView.setOnClickListener {
//                Toast.makeText(mContext, animeList[adapterPosition], Toast.LENGTH_SHORT).show()
//                mContext.startActivity(Intent(mContext,AnimeCharacterQuotes::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("anime_character",animeList[adapterPosition]).putExtra("anime",anime))
//            }
//        }

        init{
            shareContent.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Hey! Take a look at this quote.\n\n${animeList[adapterPosition]} \n\nby $anime_character from $anime")
                        type = "text/plain"

                    }

                    val shareIntent = Intent.createChooser(sendIntent, null).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(mContext,shareIntent,null)
                }

            })
        }

    }

}
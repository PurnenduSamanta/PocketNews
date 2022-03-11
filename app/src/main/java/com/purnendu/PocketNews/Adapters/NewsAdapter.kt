package com.purnendu.PocketNews.Adapters

import com.purnendu.PocketNews.Utility.Companion.checkConnection
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.purnendu.PocketNews.R
import android.annotation.SuppressLint
import android.content.Context
import com.squareup.picasso.Picasso
import android.content.Intent
import com.purnendu.PocketNews.Activities.SingleNewsActivity
import android.widget.Toast
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel
import com.squareup.picasso.Callback
import java.lang.Exception

class NewsAdapter(private val context: Context) : ListAdapter<ResponseNewsModel.Article, NewsAdapter.MyHolder>(DiffUtil())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val myView = layoutInflater.inflate(R.layout.card_layout, parent, false)
        return MyHolder(myView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val currentHeadline = getItem(position).title
        if(currentHeadline.equals("null")) holder.headline.text=""
        else holder.headline.text = currentHeadline

        val currentDescription = getItem(position).description
        if(currentDescription.equals("null")) holder.description.text=""
        else holder.description.text = currentDescription

        val currentSource = getItem(position).publishedAt
        if( currentSource.equals("null"))   holder.date.text=""
        else   holder.date.text= currentSource

        val currentPoster = getItem(position).urlToImage
        val isSuccessfullySet = booleanArrayOf(false)
        Picasso.get().load(currentPoster).placeholder(R.drawable.loading)
            .into(holder.poster, object : Callback {
                override fun onSuccess() {
                    isSuccessfullySet[0] = true
                }

                override fun onError(e: Exception) {
                    holder.poster.setImageResource(R.drawable.noimagefound)
                    isSuccessfullySet[0] = false
                }
            })
        val url = getItem(position).url
        holder.card.setOnClickListener {
            if (checkConnection(context)) {
                val intent = Intent(context, SingleNewsActivity::class.java)
                intent.putExtra("headline", currentHeadline)
                intent.putExtra("url", url)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Check your network connection", Toast.LENGTH_SHORT).show()
            }
        }
        holder.share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Checkout this news: $url")
            intent.type = "text/plain/"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.poster.setOnTouchListener { _, _ ->
            if (isSuccessfullySet[0]) {
                val animation = AnimationUtils.loadAnimation(
                    context, R.anim.news_zoom
                )
                holder.poster.startAnimation(animation)
            }
            false
        }
    }

    class MyHolder @SuppressLint("ClickableViewAccessibility") constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var headline: TextView = itemView.findViewById(R.id.headline)
        var date: TextView = itemView.findViewById(R.id.date)
        var description: TextView = itemView.findViewById(R.id.description)
        val poster: ImageView = itemView.findViewById(R.id.poster)
        val share: ImageView = itemView.findViewById(R.id.share)
        val card: CardView = itemView.findViewById(R.id.card)

        init {
            poster.clipToOutline = true //To make imageview with rounded corner
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ResponseNewsModel.Article>() {
        override fun areItemsTheSame(oldItem: ResponseNewsModel.Article, newItem: ResponseNewsModel.Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ResponseNewsModel.Article, newItem: ResponseNewsModel.Article): Boolean {
            return oldItem == newItem
        }
    }
}
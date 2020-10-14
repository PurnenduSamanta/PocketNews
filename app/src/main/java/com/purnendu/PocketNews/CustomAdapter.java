package com.purnendu.PocketNews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {

    private final Context context;
    private final ArrayList<String>news_headline,news_description,news_poster,news_source,news_url;
    private final LayoutInflater layoutInflater;

    CustomAdapter(Context context,ArrayList<String>news_headline,ArrayList<String>news_description,ArrayList<String>news_poster,ArrayList<String>news_source,ArrayList<String>news_url)
    {
      this.context=context;
      this.news_headline=news_headline;
      this.news_source=news_source;
      this.news_description=news_description;
      this.news_poster=news_poster;
      this.news_url=news_url;
      layoutInflater=LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview=layoutInflater.inflate(R.layout.card_layout,parent,false);
        MyHolder newholder= new MyHolder(myview);
        return newholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        try {
             String currentHeadline = news_headline.get(position);
            holder.headline.setText(currentHeadline);
            String currentDescription = news_description.get(position);
            holder.description.setText(currentDescription);
            String currentSource = news_source.get(position);
            holder.date.setText(currentSource);
            String currentPoster = news_poster.get(position);
            if (!currentPoster.equals("noimage")) {
                Picasso.with(context).load(currentPoster).into(holder.poster);
            } else {
                holder.poster.setImageResource(R.drawable.noimagefound);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Something Wrong happened try reinstalling application", Toast.LENGTH_LONG).show();
        }
        final String url=news_url.get(position);
        holder.card.setOnClickListener(new View.OnClickListener() {

             final String currentHeadline = news_headline.get(position);
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,news.class);
                intent.putExtra("headline",currentHeadline);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                if(url!=null) {
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    intent.setType("text/plain/");
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return news_headline.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
       public TextView headline,date,description;
       public ImageView poster,share;
       public CardView card;
       public MyHolder(@NonNull View itemView) {
            super(itemView);
            headline=itemView.findViewById(R.id.headline);
            date=itemView.findViewById(R.id.date);
            description=itemView.findViewById(R.id.description);
            poster=itemView.findViewById(R.id.poster);
            card=itemView.findViewById(R.id.card);
            share=itemView.findViewById(R.id.share);
        }
    }
}

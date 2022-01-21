package com.purnendu.PocketNews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Activities.news;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyBookmarkHolder>
{

    private final Context c;
    private  final ArrayList<String>news_title,news_url;
    private final LayoutInflater layoutInflater;

    public BookmarkAdapter(Context c, ArrayList<String> news_title, ArrayList<String> news_url) {
        this.c = c;
        this.news_title = news_title;
        this.news_url = news_url;
        layoutInflater= LayoutInflater.from(this.c);
    }

    @NonNull
    @Override
    public MyBookmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview=layoutInflater.inflate(R.layout.single_bookmark,parent,false);
        BookmarkAdapter.MyBookmarkHolder newholder= new MyBookmarkHolder(myview);
        return newholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookmarkHolder holder, int position) {
        String currentHeadline=news_title.get(position);
        holder.bookmark_header.setText(currentHeadline);
        final String currentUrl=news_url.get(position);
        holder.bookmark_url.setText(currentUrl);
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c, news.class);
                intent.putExtra("url",currentUrl);
                c.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return news_title.size();
    }

    public static class MyBookmarkHolder extends RecyclerView.ViewHolder
    {
        TextView bookmark_header,bookmark_url;
        RelativeLayout bookmark;

        public MyBookmarkHolder(@NonNull View itemView) {
            super(itemView);
            bookmark_header=itemView.findViewById(R.id.bookmark_header);
            bookmark_url=itemView.findViewById(R.id.bookmark_url);
            bookmark=itemView.findViewById(R.id.bookmark);
        }
    }

}

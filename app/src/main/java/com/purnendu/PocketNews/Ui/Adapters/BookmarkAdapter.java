package com.purnendu.PocketNews.Ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Ui.Activities.SingleNewsActivity;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.RoomDb.BookmarksTableModel;
import com.purnendu.PocketNews.Utility;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyBookmarkHolder>
{

    private final Context c;
    private  final ArrayList<BookmarksTableModel>bookmarks;
    private final LayoutInflater layoutInflater;

    public BookmarkAdapter(Context c, ArrayList<BookmarksTableModel> bookmarks) {
        this.c = c;
        this.bookmarks=bookmarks;
        layoutInflater= LayoutInflater.from(this.c);
    }

    @NonNull
    @Override
    public MyBookmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView=layoutInflater.inflate(R.layout.single_bookmark,parent,false);
        MyBookmarkHolder newHolder= new MyBookmarkHolder(myView);
        return newHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookmarkHolder holder, int position) {
        String currentHeadline=bookmarks.get(position).getTitle();
        holder.bookmark_header.setText(currentHeadline);
        String currentUrl=bookmarks.get(position).getNewsUrl();
        holder.bookmark_url.setText(currentUrl);
        holder.bookmark.setOnClickListener(v -> {

            if(!Utility.Companion.checkConnection(c))
            {
                Toast.makeText(c, "Connection not available", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent=new Intent(c, SingleNewsActivity.class);
            intent.putExtra("url",currentUrl);
            intent.putExtra("headline",currentHeadline);
            c.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {

        return bookmarks.size();
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

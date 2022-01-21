package com.purnendu.PocketNews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.Others.Operations;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Activities.news;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {

    private final Context context;
    private final ArrayList<NewsModel.articles> newsList;
    private final LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<NewsModel.articles> list) {
        this.context = context;
        newsList = list;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = layoutInflater.inflate(R.layout.card_layout, parent, false);
        return new MyHolder(myview);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        String currentHeadline = newsList.get(position).getTitle();
        holder.headline.setText(currentHeadline);
        String currentDescription = newsList.get(position).getDescription();
        holder.description.setText(currentDescription);
        String currentSource = newsList.get(position).getDate();
        holder.date.setText(currentSource);
        String currentPoster = newsList.get(position).getImageUrl();
        final boolean[] isSuccessfullySet = {false};
        Picasso.get().load(currentPoster).placeholder(R.drawable.loading).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {
                isSuccessfullySet[0] =true;
            }

            @Override
            public void onError(Exception e) {
                holder.poster.setImageResource(R.drawable.noimagefound);
                isSuccessfullySet[0] =false;
            }
        });
        String url = newsList.get(position).getNewsUrl();
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Operations.isNetworkConnected(context)) {
                    Intent intent = new Intent(context, news.class);
                    intent.putExtra("headline", currentHeadline);
                    intent.putExtra("url", url);
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, "Check your network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                if (url != null) {
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    intent.setType("text/plain/");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.poster.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isSuccessfullySet[0]) {
                    Animation animation_zoom = AnimationUtils.loadAnimation(context, R.anim.news_zoom);
                    holder.poster.startAnimation(animation_zoom);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TextView headline, date, description;
        public ImageView poster, share;
        public CardView card;

        @SuppressLint("ClickableViewAccessibility")
        public MyHolder(@NonNull final View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.headline);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            poster = itemView.findViewById(R.id.poster);
            card = itemView.findViewById(R.id.card);
            share = itemView.findViewById(R.id.share);

            poster.setClipToOutline(true);    //To make imageview with rounded corner
        }
    }
}

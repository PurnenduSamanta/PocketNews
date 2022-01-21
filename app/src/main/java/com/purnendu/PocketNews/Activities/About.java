package com.purnendu.PocketNews.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.purnendu.PocketNews.R;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView hyperlink=findViewById(R.id.hyperlink);
        hyperlink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        hyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(About.this, NewsApi.class);
                startActivity(intent);
            }
        });
    }
}
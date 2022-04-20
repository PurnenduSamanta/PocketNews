package com.purnendu.PocketNews.Ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.purnendu.PocketNews.NewsCategories;
import com.purnendu.PocketNews.R;

public class Category extends AppCompatActivity implements View.OnClickListener {


    protected ImageButton gn, hn, sn, en, bn, spn, tgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        gn = findViewById(R.id.gn);
        hn = findViewById(R.id.hn);
        sn = findViewById(R.id.sn);
        en = findViewById(R.id.en);
        bn = findViewById(R.id.bn);
        spn = findViewById(R.id.spn);
        tgn = findViewById(R.id.tgn);
        gn.setOnClickListener(this);
        hn.setOnClickListener(this);
        sn.setOnClickListener(this);
        en.setOnClickListener(this);
        bn.setOnClickListener(this);
        spn.setOnClickListener(this);
        tgn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Category.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int y = v.getId();
        switch (y) {
            case (R.id.gn):
                intent.putExtra("fragment", NewsCategories.GENERAL.getCategoryName());
                Toast.makeText(this, "General News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.hn):
                intent.putExtra("fragment", NewsCategories.HEALTH.getCategoryName());
                Toast.makeText(this, "Health News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.sn):
                intent.putExtra("fragment", NewsCategories.SCIENCE.getCategoryName());
                Toast.makeText(this, "Science News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.en):
                intent.putExtra("fragment", NewsCategories.ENTERTAINMENT.getCategoryName());
                Toast.makeText(this, "Entertainment News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.bn):
                intent.putExtra("fragment", NewsCategories.BUSINESS.getCategoryName());
                Toast.makeText(this, "Business News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.spn):
                intent.putExtra("fragment", NewsCategories.SPORTS.getCategoryName());
                Toast.makeText(this, "Sports News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case (R.id.tgn):
                intent.putExtra("fragment", NewsCategories.TECHNOLOGY.getCategoryName());
                Toast.makeText(this, "Tech News Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            default:
                intent.putExtra("fragment", (Bundle) null);
                startActivity(intent);
                break;


        }
    }
}
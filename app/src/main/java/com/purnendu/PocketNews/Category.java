package com.purnendu.PocketNews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Category extends AppCompatActivity implements View.OnClickListener {


    protected   TextView gn,hn,sn,en,bn,spn,tgn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        gn=findViewById(R.id.gn);
        hn=findViewById(R.id.hn);
        sn=findViewById(R.id.sn);
        en=findViewById(R.id.en);
        bn=findViewById(R.id.bn);
        spn=findViewById(R.id.spn);
        tgn=findViewById(R.id.tgn);
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
        Intent intent;
        int y=v.getId();
        switch (y)
        {
            case(R.id.gn):
                 intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","general news");
                startActivity(intent);
                break;
            case(R.id.hn):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","health news");
                startActivity(intent);
                break;
            case(R.id.sn):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","science news");
                startActivity(intent);
                break;
            case(R.id.en):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","entertainment news");
                startActivity(intent);
                break;
            case(R.id.bn):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","business news");
                startActivity(intent);
                break;
            case(R.id.spn):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","sports news");
                startActivity(intent);
                break;
            case(R.id.tgn):
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment","tech news");
                startActivity(intent);
                break;
            default:
                intent=new Intent(Category.this,MainActivity.class);
                intent.putExtra("fragment", (Bundle) null);
                startActivity(intent);
                break;


        }
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Category.this,MainActivity.class);
        startActivity(intent);
    }
}
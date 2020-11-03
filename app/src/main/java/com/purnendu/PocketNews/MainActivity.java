package com.purnendu.PocketNews;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.util.Objects;
public class MainActivity extends AppCompatActivity
{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String swipe="trending";
    private NavigationView snv;
    protected BottomNavigationView buttonnav;
    protected FloatingActionButton fab;
    protected DrawerLayout drawer;
    protected Fragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout=findViewById(R.id.mSwipeRefreshLayout);
        buttonnav=findViewById(R.id.buttonnav);
        fab=findViewById(R.id.fab);
        drawer=findViewById(R.id.drawer);
        snv = findViewById(R.id.snv);
            swipe();
            Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            View view = getSupportActionBar().getCustomView();
            ImageView hamburger=view.findViewById(R.id.hamburger);
            hamburger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(drawer.isDrawerOpen(snv))
                   {
                       drawer.closeDrawer(GravityCompat.START);
                   }
                   else
                   {
                       drawer.openDrawer(GravityCompat.START);
                   }
                }
            });



            snv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.bookmarks) {

                       Intent intent=new Intent(MainActivity.this,Bookmarks.class);
                       startActivity(intent);
                    }
                    else if(item.getItemId()==R.id.category)
                    {
                        Intent intent =new Intent(MainActivity.this,Category.class);
                        startActivity(intent);
                    }
                    else if (item.getItemId() == R.id.setting) {
                        Intent intent=new Intent(MainActivity.this,setting.class);
                        startActivity(intent);
                    }
                    else if (item.getItemId() == R.id.rating) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(
                                    "https://play.google.com/store/apps/details?id=com.purnendu.PocketNews"));
                            intent.setPackage("com.android.vending");
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, "You don't have any app that can open this ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (item.getItemId() == R.id.feedback) {
                        try {
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"joysamanta123@gmail.com"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "PocketNews");
                            email.setType("message/rfc822");
                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                        catch (ActivityNotFoundException e)
                        {
                            Toast.makeText(MainActivity.this, "You don't have any app that can open this ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (item.getItemId() == R.id.about) {
                      Intent intent =new Intent(MainActivity.this,About.class);
                      startActivity(intent);
                    }
                    return true;
                }
            });
        buttonnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 fragment=null;
                switch (item.getItemId())
                {
                    case(R.id.trending):
                       fragment=new FragmnentTrending();
                       swipe="trending";
                        break;
                    case(R.id.entertainment):
                        fragment=new FragmentEntertainment();
                        swipe="entertainment";
                        break;
                    case(R.id.bussiness):
                        fragment=new FragmentBussiness();
                        swipe="business";
                        break;
                    case(R.id.tech):
                        fragment=new FragmentTech();
                        swipe="tech";
                        break;
                    case(R.id.sports):
                        fragment=new FragmentSports();
                        swipe="sports";
                        break;

                }
                if(fragment!=null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    return true;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        String categoryFragment=getIntent().getStringExtra("fragment");
        if(categoryFragment !=null)
        {
            fragment=null;
            switch (categoryFragment)
                    {
                        case("general news") :
                            fragment=new FragmnentTrending();
                            swipe="trending";
                            break;
                        case("health news") :
                            fragment=new FragmentHealth();
                            swipe="health";
                            break;
                        case("science news") :
                            fragment=new FragmentScience();
                            swipe="science";
                            break;
                        case("entertainment news") :
                            fragment=new FragmentEntertainment();
                            swipe="entertainment";
                            break;
                        case("business news") :
                            fragment=new FragmentBussiness();
                            swipe="business";
                            break;
                        case("sports news") :
                            fragment=new FragmentSports();
                            swipe="sports";
                            break;
                        case("tech news") :
                            fragment=new FragmentTech();
                            swipe="tech";
                            break;
                    }
            if(fragment!=null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
            }
        }

       else
        {
            Fragment fragment=new FragmnentTrending();
            swipe="trending";
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_dialogbox);
                final EditText search =  dialog.findViewById(R.id.search);
                Button proceed= dialog.findViewById(R.id.proceed);
                Button cancel= dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(search.getText().toString().equals(""))
                        {
                            Toast.makeText(MainActivity.this, "Nothing Input", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String keyWord=search.getText().toString();
                            String url="https://newsapi.org/v2/everything?q="+keyWord+"&apiKey=Ff4fdc1a967240ca9d03b810d90e64ff";
                            Bundle bundle = new Bundle();
                            bundle.putString("url", url);
                            FragmentSearch fragobj = new FragmentSearch();
                            fragobj.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragobj).commit();
                            dialog.cancel();
                        }
                    }
                });

             dialog.show();

            }
        });



    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
    private void swipe()
    {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragment=null;
                if(swipe!=null && !swipe.equals("")) {
                    switch (swipe) {
                        case ("trending"):
                            fragment = new FragmnentTrending();
                            break;
                        case ("entertainment"):
                            fragment = new FragmentEntertainment();
                            break;
                        case ("business"):
                            fragment = new FragmentBussiness();
                            break;
                        case ("tech"):
                            fragment = new FragmentTech();
                            break;
                        case ("sports"):
                            fragment = new FragmentSports();
                            break;
                        case ("health"):
                            fragment = new FragmentHealth();
                            break;
                        case ("science"):
                            fragment = new FragmentScience();
                            break;
                    }
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }
}
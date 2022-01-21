package com.purnendu.PocketNews.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import com.purnendu.PocketNews.Fragments.FragmentBusiness;
import com.purnendu.PocketNews.Fragments.FragmentEntertainment;
import com.purnendu.PocketNews.Fragments.FragmentHealth;
import com.purnendu.PocketNews.Fragments.FragmentScience;
import com.purnendu.PocketNews.Fragments.FragmentSearch;
import com.purnendu.PocketNews.Fragments.FragmentSports;
import com.purnendu.PocketNews.Fragments.FragmentTech;
import com.purnendu.PocketNews.Fragments.FragmentTrending;
import com.purnendu.PocketNews.R;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String swipe = "trending";
    private NavigationView snv;
    protected BottomNavigationView buttonNav;
    protected FloatingActionButton fab;
    protected DrawerLayout drawer;
    protected Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        buttonNav = findViewById(R.id.buttonnav);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer);
        snv = findViewById(R.id.snv);

        buttonNav.setBackground(null);//  Setting no background color on button navigation bar

        Toolbar toolbar = findViewById(R.id.toolbar);//Custom ActionBar

        swipe();

        Objects.requireNonNull(getSupportActionBar()).hide();//Hiding original Action bar

        ImageView hamburger = toolbar.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(snv))
                    drawer.closeDrawer(GravityCompat.START);
                else
                    drawer.openDrawer(GravityCompat.START);
            }
        });


        snv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bookmarks) {
                    Intent intent = new Intent(MainActivity.this, Bookmarks.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.category) {
                    Intent intent = new Intent(MainActivity.this, Category.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.setting) {
                    Intent intent = new Intent(MainActivity.this, setting.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.rating) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(
                                "https://play.google.com/store/apps/details?id=com.purnendu.PocketNews"));
                        intent.setPackage("com.android.vending");
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, "You don't have any app that can open this ", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.feedback) {
                    try {
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"joysamanta123@gmail.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "PocketNews");
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, "You don't have any app that can open this ", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.about) {
                    Intent intent = new Intent(MainActivity.this, About.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        buttonNav.setOnItemSelectedListener(item -> {
            fragment = null;
            switch (item.getItemId()) {
                case (R.id.trending):
                    fragment = new FragmentTrending();
                    swipe = "trending";
                    break;
                case (R.id.entertainment):
                    fragment = new FragmentEntertainment();
                    swipe = "entertainment";
                    break;
                case (R.id.bussiness):
                    fragment = new FragmentBusiness();
                    swipe = "business";
                    break;
                case (R.id.sports):
                    fragment = new FragmentSports();
                    swipe = "sports";
                    break;

            }
            if (fragment != null) {
                if (!isNetworkConnected())
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).replace(R.id.frame_container, fragment).commit();
                return true;
            } else {
                Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        String categoryFragment = getIntent().getStringExtra("fragment");
        if (categoryFragment != null) {
            fragment = null;
            switch (categoryFragment) {
                case ("general news"):
                    fragment = new FragmentTrending();
                    swipe = "trending";
                    buttonNav.getMenu().findItem(R.id.trending).setChecked(true);
                    break;
                case ("health news"):
                    fragment = new FragmentHealth();
                    swipe = "health";
                    buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
                    break;
                case ("science news"):
                    fragment = new FragmentScience();
                    swipe = "science";
                    buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
                    break;
                case ("entertainment news"):
                    fragment = new FragmentEntertainment();
                    swipe = "entertainment";
                    buttonNav.getMenu().findItem(R.id.entertainment).setChecked(true);
                    break;
                case ("business news"):
                    fragment = new FragmentBusiness();
                    swipe = "business";
                    buttonNav.getMenu().findItem(R.id.bussiness).setChecked(true);
                    break;
                case ("sports news"):
                    fragment = new FragmentSports();
                    swipe = "sports";
                    buttonNav.getMenu().findItem(R.id.sports).setChecked(true);
                    break;
                case ("tech news"):
                    fragment = new FragmentTech();
                    swipe = "tech";
                    buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
                    break;
            }
            if (fragment != null) {
                if (!isNetworkConnected())
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            } else {
                Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!isNetworkConnected())
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            Fragment fragment = new FragmentTrending();
            swipe = "trending";
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_dialogbox);
                final EditText search = dialog.findViewById(R.id.search);
                Button proceed = dialog.findViewById(R.id.proceed);
                Button cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (search.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Nothing Input", Toast.LENGTH_SHORT).show();
                        } else {
                            buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
                            String keyWord = search.getText().toString().trim();
                            Bundle bundle = new Bundle();
                            bundle.putString("KEYWORD", keyWord);
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

    private void swipe() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragment = null;
                if (swipe != null && !swipe.equals("")) {
                    switch (swipe) {
                        case ("trending"):
                            fragment = new FragmentTrending();
                            break;
                        case ("entertainment"):
                            fragment = new FragmentEntertainment();
                            break;
                        case ("business"):
                            fragment = new FragmentBusiness();
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
                        if (!isNetworkConnected())
                            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
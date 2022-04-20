package com.purnendu.PocketNews.Ui.Activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.purnendu.PocketNews.Ui.Fragments.FragmentBusiness;
import com.purnendu.PocketNews.Ui.Fragments.FragmentEntertainment;
import com.purnendu.PocketNews.Ui.Fragments.FragmentHealth;
import com.purnendu.PocketNews.Ui.Fragments.FragmentScience;
import com.purnendu.PocketNews.Ui.Fragments.FragmentSearch;
import com.purnendu.PocketNews.Ui.Fragments.FragmentSports;
import com.purnendu.PocketNews.Ui.Fragments.FragmentTech;
import com.purnendu.PocketNews.Ui.Fragments.FragmentTrending;
import com.purnendu.PocketNews.NewsCategories;
import com.purnendu.PocketNews.Utility;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Ui.ViewModel.MainViewModel;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NavigationView snv;
    private BottomNavigationView buttonNav;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing viewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initializingView();


        buttonNav.setBackground(null);//  Setting no background color on button navigation bar


        swipe(); //For swiping

        ImageView hamburger = toolbar.findViewById(R.id.hamburger);// Hamburger Icon

        //After clicking Hamburger Icon
        hamburger.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(snv))
                drawer.closeDrawer(GravityCompat.START);
            else
                drawer.openDrawer(GravityCompat.START);
        });

        //Controlling SideNavigation Drawer
        snv.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bookmarks) {
                Intent intent = new Intent(MainActivity.this, Bookmarks.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.category) {
                Intent intent = new Intent(MainActivity.this, Category.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.setting) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.rating) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(
                            "https://play.google.com/store/apps/details?id=com.purnendu.PocketNews"));
                    intent.setPackage("com.android.vending");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "Don't find any Application to perform this operation", Toast.LENGTH_SHORT).show();
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
        });

        //Controlling BottomNavigationDrawer
        buttonNav.setOnItemSelectedListener(item -> {
            viewModel.changeFragment(null);
            switch (item.getItemId()) {
                case (R.id.trending):
                    viewModel.changeFragment(new FragmentTrending());
                    viewModel.changeSwipe(NewsCategories.GENERAL.getCategoryName());
                    break;
                case (R.id.entertainment):
                    viewModel.changeFragment(new FragmentEntertainment());
                    viewModel.changeSwipe(NewsCategories.ENTERTAINMENT.getCategoryName());
                    break;
                case (R.id.bussiness):
                    viewModel.changeFragment(new FragmentBusiness());
                    viewModel.changeSwipe(NewsCategories.BUSINESS.getCategoryName());
                    break;
                case (R.id.sports):
                    viewModel.changeFragment(new FragmentSports());
                    viewModel.changeSwipe(NewsCategories.SPORTS.getCategoryName());
                    break;

            }
            if (viewModel.getFragment() != null) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).replace(R.id.frame_container, viewModel.getFragment()).commit();
                return true;
            } else {
                Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        //Getting categoryFragment name from Category
        String categoryFragment = getIntent().getStringExtra("fragment");
        if (categoryFragment != null) {
            viewModel.changeFragment(null);

            if (NewsCategories.GENERAL.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentTrending());
                viewModel.changeSwipe(NewsCategories.GENERAL.getCategoryName());
                buttonNav.getMenu().findItem(R.id.trending).setChecked(true);
            } else if (NewsCategories.HEALTH.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentHealth());
                viewModel.changeSwipe(NewsCategories.HEALTH.getCategoryName());
                buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
            } else if (NewsCategories.SCIENCE.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentScience());
                viewModel.changeSwipe(NewsCategories.SCIENCE.getCategoryName());
                buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
            } else if (NewsCategories.ENTERTAINMENT.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentEntertainment());
                viewModel.changeSwipe(NewsCategories.ENTERTAINMENT.getCategoryName());
                buttonNav.getMenu().findItem(R.id.entertainment).setChecked(true);
            } else if (NewsCategories.BUSINESS.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentBusiness());
                viewModel.changeSwipe(NewsCategories.BUSINESS.getCategoryName());
                buttonNav.getMenu().findItem(R.id.bussiness).setChecked(true);
            } else if (NewsCategories.SPORTS.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentSports());
                viewModel.changeSwipe(NewsCategories.SPORTS.getCategoryName());
                buttonNav.getMenu().findItem(R.id.sports).setChecked(true);
            } else if (NewsCategories.TECHNOLOGY.getCategoryName().equals(categoryFragment)) {
                viewModel.changeFragment(new FragmentTech());
                viewModel.changeSwipe(NewsCategories.TECHNOLOGY.getCategoryName());
                buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
            }
            if (viewModel.getFragment() != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, viewModel.getFragment()).commit();
            } else {
                Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
            }
        } else {
            viewModel.changeFragment(new FragmentTrending());
            viewModel.changeSwipe(NewsCategories.GENERAL.getCategoryName());
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, Objects.requireNonNull(viewModel.getFragment())).commit();
        }

        //Searching with news keyword
        fab.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.activity_dialogbox);
            EditText search = dialog.findViewById(R.id.search);
            Button proceed = dialog.findViewById(R.id.proceed);
            Button cancel = dialog.findViewById(R.id.cancel);
            cancel.setOnClickListener(v12 -> dialog.cancel());
            proceed.setOnClickListener(v1 -> {
                if (search.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Nothing Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utility.Companion.checkConnection(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Check Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                buttonNav.getMenu().findItem(R.id.menu_none).setChecked(true);
                String keyWord = search.getText().toString().trim();
                Bundle bundle = new Bundle();
                //Open search fragment
                bundle.putString("KEYWORD", keyWord);
                FragmentSearch fragment = new FragmentSearch();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                dialog.cancel();
            });

            dialog.show();

        });


    }

    private void initializingView() {

        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        buttonNav = findViewById(R.id.buttonnav);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer);
        snv = findViewById(R.id.snv);
        toolbar = findViewById(R.id.toolbar);//Custom ActionBar
    }
    private void swipe() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.changeFragment(null);
            viewModel.getSwipe();
            if (!viewModel.getSwipe().equals("")) {

                if (NewsCategories.GENERAL.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentTrending());

                else if (NewsCategories.HEALTH.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentHealth());

                else if (NewsCategories.SCIENCE.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentScience());

                else if (NewsCategories.ENTERTAINMENT.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentEntertainment());


                else if (NewsCategories.BUSINESS.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentBusiness());


                else if (NewsCategories.SPORTS.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentSports());


                else if (NewsCategories.TECHNOLOGY.getCategoryName().equals(viewModel.getSwipe()))
                    viewModel.changeFragment(new FragmentTech());


                if (viewModel.getFragment() != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, viewModel.getFragment()).commit();
                } else {
                    Toast.makeText(MainActivity.this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }
}
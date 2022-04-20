package com.purnendu.PocketNews.Ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.PocketNews.Ui.Adapters.BookmarkAdapter;
import com.purnendu.PocketNews.PocketNewsApplication;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Repository;
import com.purnendu.PocketNews.RoomDb.BookmarksTableModel;
import com.purnendu.PocketNews.Ui.ViewModel.BookmarkViewModel;
import com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory.BookmarkViewModelFactory;
import java.util.ArrayList;
import java.util.Collections;

public class Bookmarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        //Initializing ViewModel
        Repository repository = ((PocketNewsApplication) this.getApplication()).repository;
        BookmarkViewModel viewModel = new ViewModelProvider(this, new BookmarkViewModelFactory(repository)).get(BookmarkViewModel.class);

        RecyclerView bookmarkRecycler = findViewById(R.id.bookmark_recycler);
        bookmarkRecycler.setLayoutManager(new LinearLayoutManager(Bookmarks.this));

        //Set observer
        viewModel.getBookMarkList().observe(this, bookmarks -> {
            if(bookmarks.size()==0)
            {
                Toast.makeText(Bookmarks.this, "No Bookmarks ", Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<BookmarksTableModel>list=new ArrayList<>(bookmarks);
            Collections.reverse(list);
            BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(Bookmarks.this,list);
            bookmarkRecycler.setAdapter(bookmarkAdapter);
        });
    }
}
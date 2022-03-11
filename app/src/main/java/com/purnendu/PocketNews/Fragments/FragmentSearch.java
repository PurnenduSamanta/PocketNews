package com.purnendu.PocketNews.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.PocketNews.Activities.AlertDialog;
import com.purnendu.PocketNews.Adapters.NewsAdapter;
import com.purnendu.PocketNews.PocketNewsApplication;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Repository;
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.Utility;
import com.purnendu.PocketNews.ViewModel.SearchViewModel;
import com.purnendu.PocketNews.ViewModel.ViewModelFactory.SearchViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentSearch extends Fragment {
    protected RecyclerView recycler;
    protected Context context;
    private SearchViewModel viewModel;
    private NewsAdapter newsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireActivity().getApplicationContext();
        if (context == null)
            return;
        Repository repository = ((PocketNewsApplication) this.context).repository;
        String KEY =API_KEYS.secondKey;
        if (getArguments() == null)
            return;
        String keyWord = getArguments().getString("KEYWORD");
        viewModel = new ViewModelProvider(this, new SearchViewModelFactory(repository, keyWord, KEY)).get(SearchViewModel.class);
        newsAdapter = new NewsAdapter(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        recycler = view.findViewById(R.id.recycler);
        recycler.setAdapter(newsAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));


        viewModel.getSearchResult().observe(getViewLifecycleOwner(), articles -> {


            ArrayList<ResponseNewsModel.Article> list = new ArrayList<>(articles);
            if (list.size() == 0) {
                AlertDialog alertDialog;
                if (!Utility.Companion.checkConnection(context)) {
                    alertDialog = new AlertDialog(getActivity(), "No Internet Connection");
                    return;
                }
                Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT).show();
                return;
            }
            Collections.reverse(list);
            newsAdapter.submitList(list);
        });


        return view;


    }
}

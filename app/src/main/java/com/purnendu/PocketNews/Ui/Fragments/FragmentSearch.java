package com.purnendu.PocketNews.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Retrofit.ResponseHandle;
import com.purnendu.PocketNews.Ui.Activities.AlertDialog;
import com.purnendu.PocketNews.Ui.Adapters.NewsAdapter;
import com.purnendu.PocketNews.PocketNewsApplication;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Repository;
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.Ui.ViewModel.SearchViewModel;
import com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory.SearchViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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
        String KEY = API_KEYS.secondKey;
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


        viewModel.getSearchResult().observe(getViewLifecycleOwner(), it -> {

            if (it instanceof ResponseHandle.Loading) {
                requireActivity().findViewById(R.id.circularProgressBar).setVisibility(View.VISIBLE);

            } else if (it instanceof ResponseHandle.Success) {
                requireActivity().findViewById(R.id.circularProgressBar).setVisibility(View.INVISIBLE);
                ArrayList<ResponseNewsModel.Article> list = new ArrayList<>(Objects.requireNonNull(it.getData()));
                if (list.size() == 0) {
                    AlertDialog alertDialog;
                    Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Collections.reverse(list);
                newsAdapter.submitList(list);

            } else if (it instanceof ResponseHandle.Error) {
                requireActivity().findViewById(R.id.circularProgressBar).setVisibility(View.INVISIBLE);
                Toast.makeText(context, it.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        return view;


    }
}

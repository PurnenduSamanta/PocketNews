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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.PocketNews.Ui.Activities.AlertDialog;
import com.purnendu.PocketNews.Ui.Adapters.NewsAdapter;
import com.purnendu.PocketNews.NewsCategories;
import com.purnendu.PocketNews.PocketNewsApplication;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Repository;
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.RoomDb.TechNewsTableModel;
import com.purnendu.PocketNews.Utility;
import com.purnendu.PocketNews.Ui.ViewModel.FragmentViewModel;
import com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory.FragmentViewModelFactory;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentTech extends Fragment {
    protected RecyclerView recycler;
    protected Context context;
    private FragmentViewModel viewModel;
    private NewsAdapter newsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireActivity().getApplicationContext();
        if (context == null)
            return;
        Repository repository = ((PocketNewsApplication) this.context).repository;
        String countryCode = Utility.Companion.getSelectedCountryCode(context);
        viewModel = new ViewModelProvider(this, new FragmentViewModelFactory(repository, countryCode, NewsCategories.TECHNOLOGY.getCategoryName(), API_KEYS.fifthKey)).get(FragmentViewModel.class);
        newsAdapter = new NewsAdapter(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        recycler = view.findViewById(R.id.recycler);
        recycler.setAdapter(newsAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        viewModel.getTechNewsData().observe(getViewLifecycleOwner(), trendingNewsTableModels -> {

            boolean connectionStatus = Utility.Companion.checkConnection(context);

            if (trendingNewsTableModels.size() == 0) {
                AlertDialog alertDialog;
                if (!connectionStatus)
                    alertDialog = new AlertDialog(getActivity(), "No Internet Connection");
                return;
            }
            else {
                if (!connectionStatus)
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

            ArrayList<ResponseNewsModel.Article> list = new ArrayList<>();
            for (TechNewsTableModel i : trendingNewsTableModels)
                list.add(new ResponseNewsModel.Article(i.getTitle(), i.getDescription(), i.getImageUrl(), i.getNewsUrl(), i.getDate()));

            Collections.reverse(list);
            newsAdapter.submitList(list);
            Utility.Companion.scrollToTop(recycler);
        });
        return view;

    }
}
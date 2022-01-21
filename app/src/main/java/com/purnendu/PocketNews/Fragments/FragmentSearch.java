package com.purnendu.PocketNews.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Others.SearchingKeyword;

public class FragmentSearch extends Fragment
{
    protected RecyclerView recycler;
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()==null)
            return null;
        String keyWord = getArguments().getString("KEYWORD");
        if(keyWord!=null) {
            View view = inflater.inflate(R.layout.fragment, container, false);
            recycler = view.findViewById(R.id.recycler);
            context = getActivity();
            SearchingKeyword searchingKeyword=new SearchingKeyword();
            searchingKeyword.fetch(keyWord, recycler, context);
            return view;
        }
        else
        {
           return null;
        }
    }
}

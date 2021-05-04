package com.purnendu.PocketNews;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentSearch extends Fragment
{
    protected RecyclerView recycler1;
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String url = getArguments().getString("url");
        if(url!=null) {
            View view = inflater.inflate(R.layout.fragment, container, false);
            recycler1 = view.findViewById(R.id.recycler1);
            context = getActivity();
            SearchingKeyword searchingKeyword=new SearchingKeyword();
            searchingKeyword.fetch(url, recycler1, context);
            return view;
        }
        else
        {
           return null;
        }
    }
}

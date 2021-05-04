package com.purnendu.PocketNews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentEntertainment extends Fragment {


    protected RecyclerView recycler1;
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String str = sharedPreferences.getString("country", null);
            String url;
            if (str != null) {
                url = "https://newsapi.org/v2/top-headlines?country=" + str + "&category=entertainment&apiKey=32f70c60fc714320a48dda65beb24d94";
            } else {
                url = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=32f70c60fc714320a48dda65beb24d94";
            }
            View view = inflater.inflate(R.layout.fragment, container, false);
            recycler1 = view.findViewById(R.id.recycler1);
            AppData appData = new AppData();
            appData.fetch(url, recycler1, context,"entertainment");
            return view;
        }
        return null;
    }
}

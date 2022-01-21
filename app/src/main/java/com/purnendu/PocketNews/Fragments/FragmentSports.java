package com.purnendu.PocketNews.Fragments;
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

import com.purnendu.PocketNews.Others.AppData;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Retrofit.API_KEYS;

public class FragmentSports extends Fragment
{
    protected RecyclerView recycler;
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String countryCode = sharedPreferences.getString("country", "in");
            View view = inflater.inflate(R.layout.fragment, container, false);
            recycler = view.findViewById(R.id.recycler);
            AppData appData = new AppData();
            appData.fetch("sports",countryCode, API_KEYS.fifthKey, recycler, context,"sports");
            return view;
        }
        return null;
    }

}

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

public class FragmnentTrending extends Fragment {
    protected RecyclerView recycler1;
    protected Context context;
    static int activityLunchCount=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
            String str = sharedPreferences.getString("country", null);
            String url;
            if (str != null)
            {
                url = "https://newsapi.org/v2/top-headlines?country=" + str + "&apiKey=57fdf3bd8e234c4185cb19e585be76e6";
            } else {
                url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=57fdf3bd8e234c4185cb19e585be76e6";
            }
            View view = inflater.inflate(R.layout.fragment, container, false);
            recycler1 = view.findViewById(R.id.recycler1);
            if(activityLunchCount==0)
            {
                //If it 1st time launch then directly data fetch from database
                FirstTimeTrendingLaunch firstTimeTrendingLaunch=new FirstTimeTrendingLaunch(context);
                firstTimeTrendingLaunch.LoadFromDatabase(url,recycler1,"trending");
            }
            else
            {
                AppData appData = new AppData();
                appData.fetch(url, recycler1, context,"trending");
            }
            activityLunchCount++;
            return view;
        }
        return null;
    }
}

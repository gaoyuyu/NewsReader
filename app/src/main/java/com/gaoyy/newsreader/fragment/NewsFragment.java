package com.gaoyy.newsreader.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoyy.newsreader.R;
import com.gaoyy.newsreader.adapter.NewsListAdapter;
import com.gaoyy.newsreader.bean.News;
import com.gaoyy.newsreader.utils.Global;
import com.gaoyy.newsreader.utils.Tool;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


public class NewsFragment extends Fragment
{

    public NewsFragment()
    {
        // Required empty public constructor
    }

    private View rootView;
    private RecyclerView fragmentNewsRv;
    private ProgressWheel fragmentNewsProgresswheel;
    private NewsListAdapter newsListAdapter;
    private List<News> list;

    private void assignViews(View rootView)
    {
        fragmentNewsRv = (RecyclerView) rootView.findViewById(R.id.fragment_news_rv);
        fragmentNewsProgresswheel = (ProgressWheel) rootView.findViewById(R.id.fragment_news_progresswheel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_news, container, false);
        }
        assignViews(rootView);
        new NewsTask().execute();


        return rootView;
    }

    class NewsTask extends AsyncTask<String, String, List<News>>
    {

        @Override
        protected List<News> doInBackground(String... params)
        {
            Request request = new Request.Builder()
                    .url(Global.NEWS_URL + "?type="+getResources().getString(getArguments().getInt("type"))+"&key="+Global.APPKEY)
                    .build();
            try
            {
                Response response = Tool.getOkHttpClient().newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String body = response.body().string();
                if (Tool.isRepReasonSuccess(body))
                {
                    list = Tool.getNewsList(body);
                }
            }
            catch (Exception e)
            {
                Log.i(Global.TAG, "RankFragment doInBackground Exception-->" + e.toString());
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<News> newses)
        {
            super.onPostExecute(newses);
            if(newses != null)
            {
                fragmentNewsRv.setVisibility(View.VISIBLE);
                fragmentNewsProgresswheel.setVisibility(View.GONE);
                newsListAdapter = new NewsListAdapter(getActivity(),newses);
                fragmentNewsRv.setAdapter(newsListAdapter);
                fragmentNewsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }
        }
    }


}

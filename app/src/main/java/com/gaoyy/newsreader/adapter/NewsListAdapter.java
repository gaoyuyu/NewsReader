package com.gaoyy.newsreader.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gaoyy.newsreader.R;
import com.gaoyy.newsreader.bean.News;
import com.gaoyy.newsreader.utils.Tool;

import java.util.List;


/**
 * Created by gaoyy on 2016/8/24 0024.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    private List<News> data;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }


    public NewsListAdapter(Context context, List<News> data)
    {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_news, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(rootView);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        News news = data.get(position);

        Uri picUri = Uri.parse(news.getThumbnail_pic_s());
        newsViewHolder.itemNewsImg.setHierarchy(Tool.getCommonGenericDraweeHierarchy(context));
        newsViewHolder.itemNewsImg.setController(Tool.getCommonDraweeController(picUri, newsViewHolder.itemNewsImg));

        newsViewHolder.itemNewsTitle.setText(news.getTitle());
        newsViewHolder.itemNewsDate.setText(news.getAuthor_name() + "" + news.getDate());
        if(onItemClickListener != null)
        {
            newsViewHolder.itemNewsLayout.setOnClickListener(new BasicOnClickListener(newsViewHolder));
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder
    {
        private SimpleDraweeView itemNewsImg;
        private TextView itemNewsTitle;
        private TextView itemNewsDate;
        private RelativeLayout itemNewsLayout;


        public NewsViewHolder(View itemView)
        {
            super(itemView);
            itemNewsImg = (SimpleDraweeView) itemView.findViewById(R.id.item_news_img);
            itemNewsTitle = (TextView) itemView.findViewById(R.id.item_news_title);
            itemNewsDate = (TextView) itemView.findViewById(R.id.item_news_date);
            itemNewsLayout = (RelativeLayout) itemView.findViewById(R.id.item_news_layout);
        }
    }


    public void updateData(List<News> s)
    {
        this.data = s;
        notifyDataSetChanged();
    }

    private class BasicOnClickListener implements View.OnClickListener
    {
        private NewsViewHolder newsViewHolder;

        public BasicOnClickListener(NewsViewHolder newsViewHolder)
        {
            this.newsViewHolder = newsViewHolder;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_news_layout:
                    onItemClickListener.onItemClick(newsViewHolder.itemNewsLayout, newsViewHolder.getLayoutPosition());
                    break;
            }
        }
    }
}

package com.gaoyy.newsreader.bean;

import java.io.Serializable;

/**
 * Created by gaoyy on 2016/8/24 0024.
 */
public class News implements Serializable
{
    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;
    private String url;
    private String uniquekey;
    private String type;
    private String realtype;

    public String getTitle()
    {
        return title;
    }

    public String getDate()
    {
        return date;
    }

    public String getAuthor_name()
    {
        return author_name;
    }

    public String getThumbnail_pic_s()
    {
        return thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02()
    {
        return thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03()
    {
        return thumbnail_pic_s03;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUniquekey()
    {
        return uniquekey;
    }

    public String getType()
    {
        return type;
    }

    public String getRealtype()
    {
        return realtype;
    }

    @Override
    public String toString()
    {
        return "News{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", author_name='" + author_name + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                ", url='" + url + '\'' +
                ", uniquekey='" + uniquekey + '\'' +
                ", type='" + type + '\'' +
                ", realtype='" + realtype + '\'' +
                '}';
    }
}

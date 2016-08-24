package com.gaoyy.newsreader.utils;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.gaoyy.newsreader.bean.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by gaoyy on 2016/8/24 0024.
 */
public class Tool
{
    /**
     * showSnackbar
     *
     * @param view
     * @param text
     */
    public static void showSnackbar(View view, String text)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String text)
    {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * getCommonGenericDraweeHierarchy
     *
     * @param context
     * @return
     */
    public static GenericDraweeHierarchy getCommonGenericDraweeHierarchy(Context context)
    {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        return hierarchy;
    }

    /**
     * getCommonDraweeController
     *
     * @param picUri
     * @param img
     * @return
     */
    public static DraweeController getCommonDraweeController(Uri picUri, SimpleDraweeView img)
    {
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                .setUseLastFrameForPreview(true)
                .build();
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(picUri)
                .setImageDecodeOptions(decodeOptions)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .setResizeOptions(new ResizeOptions(800, 600))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(img.getController())
                .build();
        return controller;
    }

    /**
     * getOkHttpClient
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient()
    {
        OkHttpClient client = new OkHttpClient();
        return client;
    }

    /**
     * getMainJsonObj
     *
     * @param body
     * @return
     */
    public static JSONObject getMainJsonObj(String body)
    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(body);
        }
        catch (JSONException e)
        {
            Log.i(Global.TAG, "catch Exception when getMainJsonObj：" + e.toString());
        }

        return jsonObject;
    }

    /**
     * getRepReason
     *
     * @param body
     * @return
     */
    public static boolean isRepReasonSuccess(String body)
    {
        JSONObject jsonObject = getMainJsonObj(body);
        String reason = null;
        try
        {
            reason = jsonObject.getString("reason");
            Log.i(Global.TAG, "RepReason：" + reason);
        }
        catch (JSONException e)
        {
            Log.i(Global.TAG, "catch Exception when getRepCode：" + e.toString());
        }
        return reason.equals("成功的返回");
    }

    public static List<News> getNewsList(String body)
    {
        List<News> newsList = null;
        JSONObject jsonObject = getMainJsonObj(body);
        JSONObject news = null;
        try
        {
            news = (JSONObject) jsonObject.get("result");
            Gson gson = new Gson();
            newsList = gson.fromJson(news.get("data").toString(),
                    new TypeToken<List<News>>()
                    {
                    }.getType());
        }
        catch (JSONException e)
        {
            Log.i(Global.TAG, "catch Exception when getNewsJSONObject：" + e.toString());
        }
        return newsList;
    }
}

package com.gaoyy.newsreader.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gaoyy.newsreader.R;
import com.gaoyy.newsreader.adapter.NewsPagerAdapter;
import com.gaoyy.newsreader.fragment.NewsFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Toolbar mainToolbar;
    private int[] newsType = {R.string.top,R.string.shehui,R.string.guonei,R.string.guoji,
            R.string.yule,R.string.tiyu,R.string.junshi,R.string.keji,R.string.caijing,R.string.shishang};
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private NewsPagerAdapter newsPagerAdapter;

    private TabLayout mainTablayout;
    private ViewPager mainViewpager;

    private void assignViews()
    {
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mainTablayout = (TabLayout) findViewById(R.id.main_tablayout);
        mainViewpager = (ViewPager) findViewById(R.id.main_viewpager);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initData();
        assignViews();
        configViews();

    }

    private void initData()
    {
        for (int i = 0; i < newsType.length; i++)
        {
            Bundle bundle = new Bundle();
            Fragment fragment = new NewsFragment();
            bundle.putInt("type",newsType[i]);
            fragment.setArguments(bundle);
            fragmentList.add(i, fragment);
        }
    }

    private void configViews()
    {
        setStatusBar();
        mainToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mainToolbar);

        newsPagerAdapter = new NewsPagerAdapter(this,getSupportFragmentManager(), newsType, fragmentList);
        mainViewpager.setAdapter(newsPagerAdapter);
        mainViewpager.setOffscreenPageLimit(1);
        mainTablayout.setBackgroundColor(getResources().getColor(R.color.white));
        mainTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mainTablayout.setupWithViewPager(mainViewpager);
        mainTablayout.setTabsFromPagerAdapter(newsPagerAdapter);

    }

    /**
     * 设置状态栏
     */
    private void setStatusBar()
    {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
        tintManager.setStatusBarTintEnabled(true);
    }
}

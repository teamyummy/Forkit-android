package com.yummyteam.fastcampus.forkit.view.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.EateryListFragment;
import com.yummyteam.fastcampus.forkit.view.main.fragment.mypage.MyPage_Fragment;

import java.io.IOException;


public class MainView extends AppCompatActivity implements ActivityConnectInterface {

    private EateryListFragment eateryListFragment;
    private ViewPager pager;

    private MyPage_Fragment myPage_fragment;
    private PagerAdapter pagerAdapter;
    private ConnectFork connectFork;
    private TokenCache cache;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        TabLayout tab = (TabLayout)findViewById(R.id.tab);
        tab.setBackgroundResource(R.drawable.xml_border);
        tab.addTab(tab.newTab().setText("Home"));
        tab.addTab(tab.newTab().setText("MyPage"));
        pager = (ViewPager)findViewById(R.id.pager);
        eateryListFragment = new EateryListFragment();
        myPage_fragment = new MyPage_Fragment();
        myPage_fragment.setInterface(this);

       pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        //pager가 변경시에 탭을 변경시켜주는 리스너
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        //tab에서 변경시에 pager를 변경시주는 리스너
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        connectFork = new ConnectFork(this);
        cache = TokenCache.getInstance();

        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFavorite(String r_id, String like,String f_id) {
        if(like.equals("true")){
            connectFork.setRestaurantsLike(token,r_id);
        }else if(like.equals("false")){
            connectFork.setRestaurantsDislike(token,r_id,f_id);
        }

    }

    @Override
    public void getFavorite(String r_id,String f_id, String like) {
            eateryListFragment.changeMylike(r_id,f_id,like);
    }


    @Override
    public void refresh() {
        eateryListFragment = new EateryListFragment();
        myPage_fragment = new MyPage_Fragment();
        myPage_fragment.setInterface(this);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);
    }


    class PagerAdapter extends FragmentStatePagerAdapter {


        public  PagerAdapter(FragmentManager fm){
            super(fm);

        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position)
            {
                case 0:
                    fragment = eateryListFragment;
                    break;
                case 1:
                    fragment = myPage_fragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}

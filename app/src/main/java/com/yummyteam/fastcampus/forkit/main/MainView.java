package com.yummyteam.fastcampus.forkit.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.main.fragment.eatery.EateryListFragment;
import com.yummyteam.fastcampus.forkit.main.fragment.mypage.MyPage_Fragment;


public class MainView extends AppCompatActivity {

    private EateryListFragment eateryListFragment;
    private ViewPager pager;
    private EateryListFragment eterFragment;
    private MyPage_Fragment myPage_fragment;
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

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        //pager가 변경시에 탭을 변경시켜주는 리스너
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        //tab에서 변경시에 pager를 변경시주는 리스너
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                eateryListFragment.initData();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
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

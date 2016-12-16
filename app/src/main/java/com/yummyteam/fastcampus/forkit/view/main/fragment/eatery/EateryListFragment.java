package com.yummyteam.fastcampus.forkit.view.main.fragment.eatery;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.RestaurantsList;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.main.MainView;
import com.yummyteam.fastcampus.forkit.view.main.MainViewInterface;
import com.yummyteam.fastcampus.forkit.view.map.MapsActivity;
import com.yummyteam.fastcampus.forkit.view.search.SearchRestaurants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yummyteam.fastcampus.forkit.R.id.slider;


/**
 * A simple {@link Fragment} subclass.
 */
public class EateryListFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,
        View.OnClickListener, EateryListInterface, CompoundButton.OnCheckedChangeListener {


    public EateryListFragment() {
        cache = TokenCache.getInstance();
    }

    private RecyclerView restaurant_list;

    private TokenCache cache;
    private SliderLayout mDemoSlider;
    private LinearLayout filter_layout, map_layout;
    private LayoutInflater inflater;
    private ConnectFork connectFork;
    private Toolbar toolbar;
    private ImageButton ib_search_toolbar;
    private String token;
    private int page;
    private int scrolled_count;
    private String ordered;
    private String filter;
    private TextView tv_noContent;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_eatery_list, container, false);
        restaurant_list = (RecyclerView) view.findViewById(R.id.restaurant_list);
        mDemoSlider = (SliderLayout) view.findViewById(slider);
        filter_layout = (LinearLayout) view.findViewById(R.id.filter_layout);
        map_layout = (LinearLayout) view.findViewById(R.id.map_layout);
        progressBar = (ProgressBar)view.findViewById(R.id.eProgress);
        progressBar.setVisibility(View.VISIBLE);
        tv_noContent = (TextView)view.findViewById(R.id.noSearch_content);
        filter_layout.setOnClickListener(this);
        map_layout.setOnClickListener(this);
        try {
            initList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initSlider();
        connectFork = new ConnectFork(this);
        ordered = "-review_average";
        filter = "";
        toolbar = (Toolbar) view.findViewById(R.id.toolBar_search);
        ib_search_toolbar = (ImageButton) view.findViewById(R.id.ib_search_toolbar);
        ib_search_toolbar.setOnClickListener(this);
        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        page = 1;
        scrolled_count = 0;
        setToken(page);

        restaurant_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (totalItemCount == visibleItemCount + firstVisibleItem && totalItemCount > 4) {
                    if (scrolled_count == 0) {
                        page++;
                        scrolled_count++;
                        setToken(page);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                }
                Log.e("restaurant_list", "visibleItemCOunt = " + visibleItemCount + ", totalItemCount = " + totalItemCount + ", firstVisibleItem = " + firstVisibleItem);
            }
        });
        checkList = new ArrayList<>();
        return view;
    }

    public void setToken(int page) {
        if (token.equals("")) {
            connectFork.getStoreList(page + "", ordered, filter);
        } else {
            connectFork.getStoreList_withToken(token, page + "", ordered, filter);
        }
        tv_noContent.setVisibility(View.GONE);
    }

    private void initSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private ELAdapter elAdapter;

    private void initList() throws IOException {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        restaurant_list.setLayoutManager(manager);

        elAdapter = new ELAdapter();
        elAdapter.setInterface((MainView)getActivity());

        restaurant_list.setAdapter(elAdapter);

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getContext(), Detail_Restaurant.class);
//        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onStop() {
        super.onStop();
        mDemoSlider.stopAutoCycle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        Log.e("tag", "click!");
        switch (view.getId()) {
            case R.id.filter_layout:
                pop_up_filter_dialog();
                break;
            case R.id.map_layout:
                Intent intent = new Intent(getContext(), MapsActivity.class);

                //intent.putParcelableArrayListExtra("restaurants", (ArrayList<? extends Parcelable>) elAdapter.getDatas());

                RestaurantsList restaurantsList = RestaurantsList.getInstance();
                restaurantsList.setList(elAdapter.getDatas());

                startActivity(intent);
                break;
            case R.id.tv_dialog_flilter_cancle:
                infoDialog.cancel();
                checkList = new ArrayList<>();
                Log.e("tag", "click! cancle");
                break;
            case R.id.tv_dialog_flilter_ok:

                infoDialog.dismiss();
                changeList();
                break;
            case R.id.ib_search_toolbar:
                Intent searchIntent = new Intent(getContext(), SearchRestaurants.class);
                startActivity(searchIntent);
                break;
        }

    }

    public void adapterRefresh(){
        progressBar.setVisibility(View.VISIBLE);
        elAdapter.removeallData();
        page = 1;
        connectFork.getStoreList_withToken(token, page + "", ordered, filter);
    }
    private void changeList() {
        progressBar.setVisibility(View.VISIBLE);
        elAdapter.removeallData();
        if (!ordered_temp.equals("")) {
            ordered = ordered_temp;
        }

        page = 1;
        if (checkList.size() > 0) {
            for (String tag : checkList) {
                if (filter.equals("")) {
                    filter += tag;
                } else {
                    filter += "," + tag;
                }
            }
        } else {
            filter = "";
        }
        setToken(page);
        filter = "";
    }

    private TextView tv_dialog_filter_cancle, tv_dialog_filter_ok;
    private RadioGroup rg_dialog_sort;
    private CheckBox ck_dialog_korea, ck_dialog_japan, ck_dialog_china, ck_dialog_west, ck_dialog_world, ck_dialog_cafe, ck_dialog_bar;

    private AlertDialog infoDialog;
    private String ordered_temp;

    private void pop_up_filter_dialog() {

        View view = inflater.inflate(R.layout.dialog_filter, null);
        tv_dialog_filter_cancle = (TextView) view.findViewById(R.id.tv_dialog_flilter_cancle);
        tv_dialog_filter_ok = (TextView) view.findViewById(R.id.tv_dialog_flilter_ok);


        tv_dialog_filter_ok.setOnClickListener(this);
        tv_dialog_filter_cancle.setOnClickListener(this);
        rg_dialog_sort = (RadioGroup) view.findViewById(R.id.rg_dialog_sort);
        ordered_temp = "";
        rg_dialog_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int button) {
                ordered_temp = "";
                switch (button) {
                    case R.id.rb_dialog_score:
                        ordered_temp = "-review_average";
                        break;
                    case R.id.rb_dialog_review:
                        ordered_temp = "-review_count";
                        break;
                    case R.id.rb_dialog_recomend:
                        ordered_temp = "-total_like";
                        break;
                }
            }
        });

        if (ordered.equals("-review_average")) {
            rg_dialog_sort.check(R.id.rb_dialog_score);
        } else if (ordered.equals("-review_count")) {
            rg_dialog_sort.check(R.id.rb_dialog_review);
        } else if (ordered.equals("-total_like")) {
            rg_dialog_sort.check(R.id.rb_dialog_recomend);
        }

        setCheckBoxes(view);

        infoDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();

        Window window = infoDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        infoDialog.show();
    }

    @Override
    public void getList(List<Results> data) {
        progressBar.setVisibility(View.GONE);
        if (data != null) {
            elAdapter.addDatas((ArrayList) data);
            scrolled_count = 0;
        } else {
            if(page ==1){
                tv_noContent.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getContext(), "마지막 입니다.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void setCheckBoxes(View view) {

        ck_dialog_korea = (CheckBox) view.findViewById(R.id.ck_dialog_korea);
        ck_dialog_japan = (CheckBox) view.findViewById(R.id.ck_dialog_japan);
        ck_dialog_china = (CheckBox) view.findViewById(R.id.ck_dialog_china);
        ck_dialog_west = (CheckBox) view.findViewById(R.id.ck_dialog_west);
        ck_dialog_world = (CheckBox) view.findViewById(R.id.ck_dialog_world);
        ck_dialog_cafe = (CheckBox) view.findViewById(R.id.ck_dialog_cafe);
        ck_dialog_bar = (CheckBox) view.findViewById(R.id.ck_dialog_bar);


        if (checkList.contains("한식")) {
            Log.e("tag","1");
            ck_dialog_korea.setChecked(true);
        } if (checkList.contains("일식")) {
            Log.e("tag","2");
            ck_dialog_japan.setChecked(true);
        } if (checkList.contains("중식")) {
            Log.e("tag","3");
            ck_dialog_china.setChecked(true);
        } if (checkList.contains("양식")) {
            Log.e("tag","4");
            ck_dialog_west.setChecked(true);
        } if (checkList.contains("세계음식")) {
            Log.e("tag","5");
            ck_dialog_world.setChecked(true);
        } if (checkList.contains("카페")) {
            Log.e("tag","6");
            ck_dialog_cafe.setChecked(true);
        } if (checkList.contains("주점")) {
            Log.e("tag","7");
            ck_dialog_bar.setChecked(true);
        }
        ck_dialog_korea.setOnCheckedChangeListener(this);
        ck_dialog_japan.setOnCheckedChangeListener(this);
        ck_dialog_china.setOnCheckedChangeListener(this);
        ck_dialog_west.setOnCheckedChangeListener(this);
        ck_dialog_world.setOnCheckedChangeListener(this);
        ck_dialog_cafe.setOnCheckedChangeListener(this);
        ck_dialog_bar.setOnCheckedChangeListener(this);
    }

    private ArrayList<String> checkList;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        switch (compoundButton.getId()) {
            case R.id.ck_dialog_korea:
                checkListChange("한식", check);
                break;
            case R.id.ck_dialog_japan:
                checkListChange("일식", check);
                break;
            case R.id.ck_dialog_china:
                checkListChange("중식", check);
                break;
            case R.id.ck_dialog_west:
                checkListChange("양식", check);
                break;
            case R.id.ck_dialog_world:
                checkListChange("세계음식", check);
                break;
            case R.id.ck_dialog_cafe:
                checkListChange("카페", check);
                break;
            case R.id.ck_dialog_bar:
                checkListChange("주점", check);
                break;
        }

    }

    private void checkListChange(String tag, boolean check) {
        if (check) {
            if(!checkList.contains(tag)) {
                checkList.add(tag);
            }
            Log.e("tag add","tag = "+tag);
        } else {
            checkList.remove(tag);
        }
    }

    public void changeMylike(String r_id,String f_id, String like) {
        elAdapter.changeMyLike(r_id,f_id,like);
        service.refresh_allFragment(false);
    }


    private MainViewInterface service;
    public void setService(MainViewInterface service) {
        this.service = service;
    }
}

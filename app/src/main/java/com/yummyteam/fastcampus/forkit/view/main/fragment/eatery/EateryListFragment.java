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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.detail.Detail_Restaurant;
import com.yummyteam.fastcampus.forkit.view.map.MapsActivity;
import com.yummyteam.fastcampus.forkit.view.search.SearchRestaurants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yummyteam.fastcampus.forkit.R.id.slider;


/**
 * A simple {@link Fragment} subclass.
 */
public class EateryListFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener, EateryListInterface {


    public EateryListFragment() {
        // Required empty public constructor
    }

    private RecyclerView restaurant_list;


    private ArrayList<Results> datas;
    private SliderLayout mDemoSlider;
    private LinearLayout filter_layout,map_layout;
    private LayoutInflater inflater;
    private ConnectFork connectFork;
    private Toolbar toolbar;
    private ImageButton ib_search_toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        View view=  inflater.inflate(R.layout.fragment_eatery_list, container, false);
        restaurant_list = (RecyclerView)view.findViewById(R.id.restaurant_list);
        mDemoSlider = (SliderLayout)view.findViewById(slider);
        filter_layout = (LinearLayout) view.findViewById(R.id.filter_layout);
        map_layout = (LinearLayout) view.findViewById(R.id.map_layout);

        filter_layout.setOnClickListener(this);
        map_layout.setOnClickListener(this);
        initList();
        initSlider();
        connectFork = new ConnectFork(this);
        connectFork.getStoreList();
        toolbar = (Toolbar)view.findViewById(R.id.toolBar_search);
        ib_search_toolbar = (ImageButton)view.findViewById(R.id.ib_search_toolbar);
        ib_search_toolbar.setOnClickListener(this);



        return view;
    }

    private void initSlider()
    {
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        for(String name : url_maps.keySet()){
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
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }
    private ELAdapter elAdapter;
    private void initList() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        restaurant_list.setLayoutManager(manager);

        elAdapter = new ELAdapter(getActivity());

        restaurant_list.setAdapter(elAdapter);

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Detail_Restaurant.class);
        startActivity(intent);
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
        Log.e("tag","click!");
        switch (view.getId())
        {
            case R.id.filter_layout:
                pop_up_filter_dialog();
                break;
            case R.id.map_layout:
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_dialog_flilter_cancle:
                infoDialog.cancel();
                Log.e("tag","click! cancle");
                break;
            case R.id.tv_dialog_flilter_ok:
                infoDialog.dismiss();
                Log.e("tag","click! dismiss");
                break;
            case R.id.ib_search_toolbar:
                Intent searchIntent = new Intent(getContext(), SearchRestaurants.class);
                startActivity(searchIntent);
                break;
        }

    }

    private TextView tv_dialog_filter_cancle,tv_dialog_filter_ok;
    private RadioGroup rg_dialog_sort;
    private RadioButton rb_dialog_score,rb_dialog_recomend,rb_dialog_review;
    private CheckBox ck_dialog_korea,ck_dialog_japan,ck_dialog_china,ck_dialog_west,ck_dialog_world
                        ,ck_dialog_cafe,ck_dialog_bar;

    private AlertDialog infoDialog;
    private void pop_up_filter_dialog() {

        View view = inflater.inflate(R.layout.dialog_filter, null);
        tv_dialog_filter_cancle = (TextView)view.findViewById(R.id.tv_dialog_flilter_cancle);
        tv_dialog_filter_ok = (TextView)view.findViewById(R.id.tv_dialog_flilter_ok);




        tv_dialog_filter_ok.setOnClickListener(this);
        tv_dialog_filter_cancle.setOnClickListener(this);


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
        elAdapter.addDatas((ArrayList)data);
    }
}

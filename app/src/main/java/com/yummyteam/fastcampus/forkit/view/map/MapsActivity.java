package com.yummyteam.fastcampus.forkit.view.map;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.detail.Detail_Restaurant;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GetResultsInterface {

    private GoogleMap mMap;
    ViewPager pager;

    Marker selectedMarker;
    View marker_root_view;
    ImageView ig_marker;

    ArrayList<Marker> markers;
    CameraUpdate center;

    ConnectFork2 connectFork;

    CustomAdapter adapter;
    ItemReader itemReader;

    List<Item> items;

    Toolbar toolbar;
    ImageButton ib_back_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setMap();



        pager= (ViewPager)findViewById(R.id.pager);
        adapter= new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        toolbar=(Toolbar)findViewById(R.id.toolBar_sub);
        ib_back_toolbar=(ImageButton)findViewById(R.id.ib_back_toolbar);
        ib_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.this.finish();
            }
        });


        connectFork = new ConnectFork2(this);
        connectFork.getStoreList();

        setCustomMarkerView();


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                changeSelectedMarkerWithPager(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void getList(List<Results> data) {
        adapter.addData((ArrayList)data);
        items= new ItemReader((ArrayList<Results>)data).read();
        getMarkerItems();
        changeSelectedMarkerWithPager(0);

    }

    @Override
    public void getDetail(Results data) {

    }



    class CustomAdapter extends PagerAdapter {

        LayoutInflater inflater;
        ArrayList<Results> datas;



        public CustomAdapter(LayoutInflater inflater) {
            // TODO Auto-generated constructor stub
            this.inflater = inflater;
            datas=new ArrayList<>();
        }

        public void addData(ArrayList<Results> datas){

            this.datas.addAll(datas);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            final Results data =datas.get(position);

            View view = null;
            view = inflater.inflate(R.layout.item_map, null);

            CardView cardView = (CardView)view.findViewById(R.id.cardView);
            ImageView imageView=(ImageView)view.findViewById(R.id.imageView4);
            TextView tvStoreName=(TextView)view.findViewById(R.id.tvStoreName);
            TextView tvRating=(TextView)view.findViewById(R.id.tvRating);
            TextView tvAddress=(TextView)view.findViewById(R.id.tvAddress);



            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pagerAni(view);
                    loadMain(data);

                }
            });


            String img_src ="";
            if(data.getImages().size() == 0) {
                img_src = "https://yt3.ggpht.com/-Xpap6ijaRfM/AAAAAAAAAAI/AAAAAAAAAAA/eyfS-T4Pqxc/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
            }else{
                img_src = data.getImages().get(0).getImg_t();
            }
            Picasso.with(MapsActivity.this).load(img_src).into(imageView);
            Log.e("IMAGE TAG", img_src);
            Log.e("Image position", position+"");
            tvStoreName.setText(data.getName());
            tvRating.setText("★"+data.getReview_score()+"");
            tvAddress.setText(data.getAddress());


            container.addView(view);

            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View v, Object obj) {
            // TODO Auto-generated method stub
            return v == obj;
        }
    }
    private final int SPLASH_DISPLAY_LENGTH =1100;
    private void loadMain(final Results data){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (MapsActivity.this, Detail_Restaurant.class);
                intent.putExtra("restaurant_id",data.getId());
                MapsActivity.this.startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                MapsActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);


    }
    int y=0;
    int r=0;
    int x=0;
    public void pagerAni(View v){

        y=y-200;
        r=r+720;
        ObjectAnimator ani1 = ObjectAnimator.ofFloat(pager, "translationY", y);
        ObjectAnimator ani2 = ObjectAnimator.ofFloat(pager, "rotation", r);

        AnimatorSet aniset= new AnimatorSet();
        aniset.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                x=x+1600;

                ObjectAnimator ani = ObjectAnimator.ofFloat(pager, "translationX", x);

                ani.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                ani.setDuration(500);
                ani.start();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        aniset.setDuration(700);
        aniset.playTogether(ani1,ani2);
        aniset.start();
    }


    @Override
    protected  void onResume(){
        super.onResume();
        setMap();

    }

    private void setMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.515364, 127.022796), 14));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                center = CameraUpdateFactory.newLatLng(marker.getPosition());
                mMap.animateCamera(center);

                changeSelectedMarker(marker);
                pager.setCurrentItem((int)(marker.getZIndex()),true);

                return true;
            }
        });

        setCustomMarkerView();



    }
    private void changeSelectedMarkerWithPager(int position){
        if(selectedMarker !=null){
            addMarker(selectedMarker,false);
            selectedMarker.remove();
        }

        Marker marker = markers.get(position);
        selectedMarker=addMarker(marker,true);
        center = CameraUpdateFactory.newLatLng(selectedMarker.getPosition());
        mMap.animateCamera(center);


    }
    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }


    }


    private void getMarkerItems() {


        Marker marker;
        markers=new ArrayList<>();

        for (Item item : items) {
            addMarker(item, false);
            marker=addMarker(item, false);
            markers.add(marker);
        }

    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.item_marker, null);
        ig_marker = (ImageView) marker_root_view.findViewById(R.id.ig_marker);
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int order = ((int) marker.getZIndex());

        Item temp = new Item(lat, lon,order);
        return addMarker(temp, isSelectedMarker);

    }
    private Marker addMarker(Item item, boolean isSelectedMarker) {


        LatLng position = new LatLng(item.getLat(), item.getLon());

        if (isSelectedMarker) {
            ig_marker.setImageResource(R.drawable.marker_like_select);

        } else {
            ig_marker.setImageResource(R.drawable.marker_like_unselect);

        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.zIndex(item.getOrder());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;

    }
}
class Item
{
    double lat;
    double lon;
    int order;


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Item(double lat, double lon, int order){


        this.lat=lat;
        this.lon=lon;
        this.order=order;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}


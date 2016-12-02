package com.yummyteam.fastcampus.forkit.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yummyteam.fastcampus.forkit.R;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.yummyteam.fastcampus.forkit.R.id.img;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ViewPager pager;

    Marker selectedMarker;
    View marker_root_view;
    ImageView ig_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setMap();

        pager= (ViewPager)findViewById(R.id.pager);
        CustomAdapter adapter= new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);
        setCustomMarkerView();


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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.515364, 127.022796), 16));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
                mMap.animateCamera(center);

                changeSelectedMarker(marker);

                return true;
            }
        });

        setCustomMarkerView();
        getSampleMarkerItems();

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




    private void getSampleMarkerItems() {
        ArrayList<Item> sampleList = new ArrayList();


        sampleList.add(new Item(37.515364, 127.022796));
        sampleList.add(new Item(37.515301, 127.022399));
        sampleList.add(new Item(37.515559, 127.022259));


        for (Item item : sampleList) {
            addMarker(item, false);
        }

    }
    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.item_marker, null);
        ig_marker = (ImageView) marker_root_view.findViewById(R.id.ig_marker);
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        Item temp = new Item(lat, lon);
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

    private void readItems()throws JSONException{
        InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        List<Item> items =  new ItemReader().read(inputStream);

    }
    public class CustomAdapter extends PagerAdapter {

        LayoutInflater inflater;

        public CustomAdapter(LayoutInflater inflater) {
            // TODO Auto-generated constructor stub

            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            View view = null;
            view = inflater.inflate(R.layout.item_map, null);

            CardView cardview = (CardView)view.findViewById(R.id.cardView);
            ImageView imageView=(ImageView)view.findViewById(img);
            TextView textView=(TextView)view.findViewById(R.id.textView);
            TextView textView2=(TextView)view.findViewById(R.id.textView2);
            TextView textView3=(TextView)view.findViewById(R.id.textView3);

            imageView.setImageResource(R.drawable.food01+position);

            textView3.setText(position+1+".Name");



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


}
class Item
{
    double lat;
    double lon;


    public Item(double lat,double lon){


        this.lat=lat;
        this.lon=lon;
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
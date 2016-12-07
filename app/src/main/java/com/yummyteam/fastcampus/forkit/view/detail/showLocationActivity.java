package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yummyteam.fastcampus.forkit.R;

class ShowLocationActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    double lat;
    double lon;

    // 최소 GPS 정보 업데이트 거리 5미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    // 최소 GPS 정보 업데이트 시간 밀리세컨
    private static final long MIN_TIME_BW_UPDATES = 6000;
    LocationListener locationListener;
    LocationManager locationManager;
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        lat=bundle.getDouble("Res_lat");
        lon=bundle.getDouble("Res_lon");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                showCurrentLocation(location.getLatitude(),location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }else {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

            }
        }
    }


    public void showCurrentLocation(double latitude, double longitude){

        googleMap.clear();

        LatLng myLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().
                position(myLocation).title("Marker in me")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_filter_price_pressed)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,14));


        LatLng restaurantLocation= new LatLng(lat,lon);
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select)));



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        LatLng restaurantLocation= new LatLng(lat,lon);
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation,18));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

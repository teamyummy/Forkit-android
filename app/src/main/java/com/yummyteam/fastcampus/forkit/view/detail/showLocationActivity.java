package com.yummyteam.fastcampus.forkit.view.detail;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

class ShowLocationActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    Toolbar toolbar;
    ImageButton ib_back_toolbar;

    double lat;
    double lon;

    // 최소 GPS 정보 업데이트 거리 5미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    // 최소 GPS 정보 업데이트 시간 밀리세컨
    private static final long MIN_TIME_BW_UPDATES = 6000;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private GoogleMap googleMap;
    private ProgressBar smPb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            init();
        } else {
            checkPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        // 런타임 권한 체크 (디스크읽기권한)
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 요청할 권한 배열생성
            String permissionArray[] = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            // 런타임 권한요청을 위한 팝업창 출력
            requestPermissions(permissionArray, REQUEST_CODE);
        } else {
            // 런타임 권한이 이미 있으면 데이터를 세팅한다
            init();
        }

    }

    private void init() {


        smPb = (ProgressBar) findViewById(R.id.mapProgressBar2);
        smPb.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lat = bundle.getDouble("Res_lat");
        lon = bundle.getDouble("Res_lon");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolBar_sub);
        ib_back_toolbar = (ImageButton) findViewById(R.id.ib_back_toolbar);
        ib_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowLocationActivity.this.finish();
            }
        });


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                showCurrentLocation(location.getLatitude(), location.getLongitude());
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

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE: // 요청코드가 위의 팝업창에 넘겨준 코드와 같으면
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) { // 권한을 체크하고
                    // 권한이 있으면 데이터를 생성한다
                    init();
                }
                break;
        }
    }

    public void showCurrentLocation(double latitude, double longitude) {

        googleMap.clear();

        LatLng myLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().
                position(myLocation).title("Marker in me")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_filter_price_pressed)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));


        LatLng restaurantLocation = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select)));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        LatLng restaurantLocation = new LatLng(lat, lon);
        smPb.setVisibility(View.GONE);
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 14));

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

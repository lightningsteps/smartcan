package com.brackets.smartcan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import retrofit2.Callback;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Callback<List<SmartCan>> {

    private GoogleMap mMap;
    private List<SmartCan> globalCanList;
    private SmartCan cans;
    private HashMap<String, Integer> markerMap;
    private String id;
    private Timer t;
    private Intent intent;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dubrovniksmartcan-bracketscan.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISmartCanService service = retrofit.create(ISmartCanService.class);

        Call<List<SmartCan>> allCans = service.getCans();
        allCans.enqueue(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng dubrovnik = new LatLng(42.6490885, 18.0905608);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(dubrovnik));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                intent = new Intent(MapsActivity.this, SecondActivity.class);

                String sendID =  marker.getTitle().split(" ")[0];

                intent.putExtra("id",sendID);
                startActivity(intent);

                return true;
            }
        });
    }



    @Override
    public void onResponse(Call<List<SmartCan>> call, Response<List<SmartCan>> response) {

        globalCanList = response.body();

        for (SmartCan c : globalCanList) {
                LatLng position = new LatLng(c.getLatitude(),c.getLongitude());
                MarkerOptions can = new MarkerOptions().title(c.getId() + " " +c.getType()).position(position);

                //can.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                if(c.getType().equals("Garbage")) {
                    can.icon(BitmapDescriptorFactory.fromResource(R.drawable.garbage));
                } else if (c.getType().equals("Glass")) {
                can.icon(BitmapDescriptorFactory.fromResource(R.drawable.glass));
                } else if (c.getType().equals("Paper")) {
                    can.icon(BitmapDescriptorFactory.fromResource(R.drawable.paper));
                } else if (c.getType().equals("Plastic")) {
                    can.icon(BitmapDescriptorFactory.fromResource(R.drawable.plastic));
                }
                mMap.addMarker(can);

        }
    }

    @Override
    public void onFailure(Call<List<SmartCan>> call, Throwable t) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
    }
}

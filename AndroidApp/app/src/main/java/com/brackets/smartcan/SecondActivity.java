package com.brackets.smartcan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;

import static com.brackets.smartcan.R.id.progressBar;

public class SecondActivity extends AppCompatActivity implements Callback<SmartCan> {

    private SmartCan smartCan;
    private ISmartCanService service;
    private String id;
    private Call<SmartCan> smartCanCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

       //setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(0, 153, 51));
        }


        id = getIntent().getExtras().getString("id");

        Handler handler = new Handler();
        handler.postDelayed(new SmartCanService(this, id, handler), 100);
    }

    @Override
    public void onResponse(Call<SmartCan> call, final Response<SmartCan> response) {
        smartCan = response.body();

        TextView type = (TextView) findViewById(R.id.typeText);
        TextView address = (TextView) findViewById(R.id.streetText);
        TextView location = (TextView) findViewById(R.id.locationText);

        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar2);
        //bar.getProgressDrawable().setColorFilter(
                //Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

      //  bar.setProgressDrawable();

        TextView full = (TextView) findViewById(R.id.textView);
        bar.setProgress(smartCan.getCapacity());
        full.setText("Full: " + smartCan.getCapacity() + "%");

        bar.setVisibility(View.VISIBLE);

        type.setText(smartCan.getType());
        address.setText(smartCan.getAddress());
        location.setText(smartCan.getLatitude() + "," + smartCan.getLongitude());
    }


    @Override
    public void onFailure(Call<SmartCan> call, Throwable t) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
    }
}

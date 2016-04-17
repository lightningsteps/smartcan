package com.brackets.smartcan;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;

public class SmartCanService implements Runnable {

    SecondActivity context;
    String id;
    Handler handler;
    int time;
    Retrofit retrofit;

    public SmartCanService(SecondActivity context, String id, Handler handler)
    {
        this.context = context;
        this.id = id;
        this.handler = handler;
        this.time = 0;

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://dubrovniksmartcan-bracketscan.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void run() {

        time += 1000;
        ISmartCanService service = retrofit.create(ISmartCanService.class);
        Call<SmartCan> smartCanCall = service.getSmartCanById(Integer.parseInt(id));
        smartCanCall.enqueue(this.context);

        Log.d("ApiTimer", "delay: " + time);
        handler.postDelayed(this, 1000);
    }
}

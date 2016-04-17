package com.brackets.smartcan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tomica on 4/15/2016.
 */
public interface ISmartCanService {

    @GET("/cans")
    Call<List<SmartCan>> getCans();

    @GET("/cans/{id}")
    Call<SmartCan> getSmartCanById(@Path("id")int id);


}

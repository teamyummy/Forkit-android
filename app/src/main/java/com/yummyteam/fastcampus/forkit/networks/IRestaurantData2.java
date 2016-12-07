package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.Restaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dabin on 2016-12-06.
 */

public interface IRestaurantData2 {

    @GET("api/v1/{value}/")
    Call<List<Restaurants>> getRestaurantsList(@Path("value") String value);
}

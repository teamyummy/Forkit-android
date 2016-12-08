package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.RestaurantsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dabin on 2016-12-06.
 */

public interface IRestaurantData {

    @GET("api/v1/{value}/")
    Call<RestaurantsData> getRestaurantsList(@Path("value") String value);

    @GET("api/v1/{value}/")
    Call<RestaurantsData> getSearchRestaurantsList(@Path("value") String value, @Query(value = "search", encoded = true) String search );
}

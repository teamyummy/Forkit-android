package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.Auth;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Dabin on 2016-12-06.
 */

public interface IRestaurantData {

    @GET("api/v1/restaurants/")
    Call<RestaurantsData> getRestaurantsList(@QueryMap Map<String,String> query);

    @GET("api/v1/restaurants/")
    Call<RestaurantsData> getRestaurantsList(@Header("Authorization")String Authorization,@QueryMap Map<String,String> query);

    @GET("api/v1/{value}/")
    Call<RestaurantsData> getSearchRestaurantsList(@Path("value") String value, @Query(value = "search", encoded = true) String search );

    @FormUrlEncoded
    @POST("/api/v1/token-auth/")
    Call<Auth> login(@Field("username")String username,@Field("password")String password);


}

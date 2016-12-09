package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Dabin on 2016-12-06.
 */

public interface IRestaurantData2 {

    @GET("api/v1/{value}/")
    Call<RestaurantsData> getRestaurantsList(@Path("value") String value);

    @GET("api/v1/{value}/{pk}")
    Call<Results> getRestaurantsDetail(@Path("value") String value, @Path("pk") String pk);

    @FormUrlEncoded
    @POST("api/v1/{value/{pk}/{value2}")
    Call<Reviews> postReviews(@Path("value") String value, @Path("pk") String pk,@Path("value2") String value2);



}

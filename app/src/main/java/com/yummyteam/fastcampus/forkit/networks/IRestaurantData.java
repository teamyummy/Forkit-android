package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.Auth;
import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
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

    @GET("api/v1/restaurants/")
    Call<RestaurantsData> getSearchRestaurantsList(@Query(value = "search", encoded = true) String search );

    @GET("api/v1/restaurants/")
    Call<RestaurantsData> getSearchRestaurantsList_withToken(@Header("Authorization") String Authorization,@Query(value = "search", encoded = true) String search );

    @FormUrlEncoded
    @POST("/api/v1/token-auth/")
    Call<Auth> login(@Field("username")String username,@Field("password")String password);

    @POST("/api/v1/restaurants/{id}/favors/")
    Call<Favors> setRestaurantsLike(@Header("Authorization")String Authorization,@Path("id") String id);

    @DELETE("/api/v1/restaurants/{r_id}/favors/{f_id}/")
    Call<String> setRestaurantsDislike(@Header("Authorization")String Authorization,@Path("r_id") String r_id,@Path("f_id")String f_id);

    @GET("/api/v1/my/favor-rests/")
    Call<List<Results>> getMyFavorites(@Header("Authorization") String Authorization);

    @GET("/api/v1/my/author-reviews/")
    Call<List<Reviews>> getMyReviews(@Header("Authorization") String Authorization);

    @DELETE("/api/v1/restaurants/{r_id}/reviews/{id}/")
    Call<String> removeMyReviews(@Header("Authorization")String Authorization,@Path("r_id")String r_id,@Path("id") String id);
}

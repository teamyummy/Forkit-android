package com.yummyteam.fastcampus.forkit.networks;

import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.ReviewLike;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.model.ReviewImagesResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Dabin on 2016-12-06.
 */

public interface IRestaurantData2 {

    @GET("api/v1/restaurants/")
    Call<RestaurantsData> getRestaurantsList(@QueryMap Map<String,String> query);

    @GET("api/v1/{value}/{pk}/")
    Call<Results> getRestaurantsDetail(@Path("value") String value, @Path("pk") String pk);
    @GET("api/v1/restaurants/{pk}/")
    Call<Results> getRestaurantsDetailwithToken(@Header("Authorization")String token,
                                                @Path("pk") String pk);

    @FormUrlEncoded
    @POST("api/v1/restaurants/{pk}/reviews/")
    Call<Reviews> postReviews(@Header("Authorization")String token,
                              @Path("pk")String pk,
                                    @FieldMap Map<String, String> fieldMap);
    @Multipart
    @POST("api/v1/restaurants/{pk}/reviews/{pk2}/images/")
    Call<ReviewImagesResponse> postPhotos(@Header("Authorization")String token,
                                          @Part("alt") RequestBody alt, @Path("pk")String pk, @Path("pk2")String pk2,
                                          @Part MultipartBody.Part img);

    @POST("api/v1/restaurants/{pk}/favors/")
    Call<Favors> putLikeRestaurant(@Header("Authorization")String token,
                                   @Path("pk") String pk
                                                );

    @DELETE("api/v1/restaurants/{pk}/favors/{pk2}/")
    Call<Favors> deleteLikeRestaurant(@Header("Authorization")String token,@Path("pk")String pk,@Path("pk2")String pk2);

    @FormUrlEncoded
    @POST("api/v1/restaurants/{pk}/reviews/{pk2}/likes/")
    Call<ReviewLike> postLikeReview(@Header("Authorization")String token, @Path("pk")String pk, @Path("pk2")String pk2,
                                    @Field("up_and_down")String like);

    @DELETE("api/v1/restaurants/{pk}/reviews/{pk2}/likes/{pk3}/")
    Call<ReviewLike> deleteLikeReview(@Header("Authorization")String token, @Path("pk")String pk, @Path("pk2")String pk2,

                                      @Path("pk3")String pk3);

    @FormUrlEncoded
    @PUT("api/v1/restaurants/{pk}/reviews/{pk2}/likes/{pk3}/")
    Call<ReviewLike> putLikeReview(@Header("Authorization")String token, @Path("pk")String pk, @Path("pk2")String pk2,

                                @Path("pk3")String pk3,@Field("up_and_down")String like);
    @GET("api/v1/restaurants/{pk}/reviews/{pk2}/likes/{pk3}/")
    Call <ReviewLike> getLikeReview(@Header("Authorization")String token, @Path("pk")String pk, @Path("pk2")String pk2,

                                    @Path("pk3")String pk3,@Field("up_and_down")String like);



}

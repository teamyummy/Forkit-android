package com.yummyteam.fastcampus.forkit.networks;

import android.util.Log;

import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dabin on 2016-12-06.
 */

public class ConnectFork2 {
    String baseUrl = "http://mangoplates.com/";
    List<Results> data;
    Results detailData;
    GetResultsInterface mListener;
    String id;
    public ConnectFork2(GetResultsInterface mListener){
        this.mListener = mListener;


    }
    public void getStoreList() {

        //1. 레트로핏 설정
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData2 service = client.create(IRestaurantData2.class);
        String value = "restaurants";

        Call<RestaurantsData> remoteData = service.getRestaurantsList(value);
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag","connect start");
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if(response.isSuccessful()){
                    Log.e("tag1","size = " + response.body());
                    data = response.body().getResults();
                    mListener.getList(data);
                }else{
                    Log.e("tag2",response.message()+call.request().body());
                    data = null;
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("onFailure","request body ="+call.request().body());
                t.printStackTrace();
                data = null;
            }


        });

    }
    public void getStoreDetail(String id){

        String pk=id;
        String value = "restaurants";

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData2 service = client.create(IRestaurantData2.class);
        Call<Results> remoteData = service.getRestaurantsDetail(value,pk);
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag","connect start");
        remoteData.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()){
                    Log.e("tag1","size = " + response.body());
                    detailData = response.body();
                    Log.e("Tag3","detailData=" + detailData);
                    mListener.getDetail(detailData);
                }else{
                    Log.e("tag2",response.message()+call.request().body());
                    detailData = null;
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e("onFailure","request body ="+call.request().body());
                t.printStackTrace();
                data = null;
            }


        });
    }


}

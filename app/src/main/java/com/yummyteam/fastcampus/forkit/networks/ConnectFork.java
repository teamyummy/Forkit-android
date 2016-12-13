package com.yummyteam.fastcampus.forkit.networks;

import android.util.Log;

import com.yummyteam.fastcampus.forkit.model.Auth;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.view.login.LoginInterface;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.EateryListInterface;
import com.yummyteam.fastcampus.forkit.view.search.SearchInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dabin on 2016-12-06.
 */

public class ConnectFork {
    String baseUrl = "http://mangoplates.com/";
    List<Results> data;
    EateryListInterface eListener;
    SearchInterface sListener;
    LoginInterface lListener;
    public ConnectFork(EateryListInterface eListener){
        this.eListener = eListener;

    }
    public ConnectFork(SearchInterface sListener){
        this.sListener = sListener;
    }

    public ConnectFork(LoginInterface lListener) {
        this.lListener = lListener;
    }

    public void getStoreList(String page,String ordered, String tags) {

        //1. 레트로핏 설정
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData service = client.create(IRestaurantData.class);
        Map<String,String> query = new HashMap<>();
        query.put("page",page);
        query.put("ordering",ordered);
        if(tags.length()>0){
            query.put("tags",tags);
        }
        Call<RestaurantsData> remoteData = service.getRestaurantsList(query);
        Log.e("connect","url = "+remoteData.request().url().toString());
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag","connect start = " +remoteData.request().url().toString());
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if(response.isSuccessful()){
                    Log.e("tag1","size = " + response.body());
                    data = response.body().getResults();
                    eListener.getList(data);
                }else{
                    Log.e("tag2",response.message()+call.request().body());
                    eListener.getList(null);
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

    public void searchRestaurants(String search){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData service = client.create(IRestaurantData.class);
        String value ="restaurants";
        Call<RestaurantsData> remoteData = service.getSearchRestaurantsList(value,search);
        Log.e("tag",remoteData.request().url().toString());
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()){
                    Log.e("respone","success");
                    sListener.setResult(response.body().getResults());
                }else{
                    Log.e("respone","fail");
                    List<Results> list = new ArrayList<Results>();
                    sListener.setResult(list);
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("connect","failure");
                t.printStackTrace();
            }
        });
    }

    public void login(String name,String passwd){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData service = client.create(IRestaurantData.class);

        final Call<Auth> remoteData = service.login(name,passwd);
        Log.e("tag",remoteData.request().url().toString());
        remoteData.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {


                if(response.isSuccessful()){
                    Log.e("tag",response.message().toString());
                    //Log.e("tag",response.errorBody().toString());
                    //Log.e("tag",response.headers().toString());
                    lListener.setToken(response.body().getToken());
                }else{

                    Log.e("auth","respone fail");
                    lListener.setToken("");
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Log.e("auth","connect fail");
                lListener.setToken("");
            }
        });

    }

    public void getStoreList_withToken(String token,String page,String ordered,String tags) {
        //1. 레트로핏 설정
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface

        String token_complete = "token " + token;
        IRestaurantData service = client.create(IRestaurantData.class);
        Map<String,String> query = new HashMap<>();
        query.put("page",page);
        query.put("ordering",ordered);
        if(tags.length()>0){
            query.put("tags",tags);
        }
        Call<RestaurantsData> remoteData = service.getRestaurantsList(token_complete,query);
        Log.e("connect","url = "+remoteData.request().url().toString());
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag","connect start");
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if(response.isSuccessful()){
                    Log.e("tag1","size = " + response.body().getResults().size());
                    data = response.body().getResults();
                    eListener.getList(data);
                }else{
                    Log.e("tag2",response.message()+call.request().body());
                    data = null;
                    eListener.getList(data);
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
}


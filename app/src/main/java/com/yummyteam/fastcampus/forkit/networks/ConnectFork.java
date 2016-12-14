package com.yummyteam.fastcampus.forkit.networks;

import android.util.Log;

import com.yummyteam.fastcampus.forkit.model.Auth;
import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.view.login.LoginInterface;
import com.yummyteam.fastcampus.forkit.view.main.ActivityConnectInterface;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.EateryListInterface;
import com.yummyteam.fastcampus.forkit.view.main.fragment.mypage.MyPageInterface;
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
    private String baseUrl = "http://mangoplates.com/";
    private List<Results> data;
    private EateryListInterface eListener;
    private SearchInterface sListener;
    private LoginInterface lListener;
    private ActivityConnectInterface anInterface;
    private MyPageInterface mpInterface;

    public ConnectFork(EateryListInterface eListener) {
        this.eListener = eListener;

    }

    public ConnectFork(SearchInterface sListener) {
        this.sListener = sListener;
    }

    public ConnectFork(LoginInterface lListener) {
        this.lListener = lListener;
    }

    public ConnectFork(ActivityConnectInterface anInterface) {
        this.anInterface = anInterface;
    }

    public ConnectFork(MyPageInterface mpInterface) {
        this.mpInterface = mpInterface;
    }

    public void getStoreList(String page, String ordered, String tags) {


        HashMap<String,String> query = (HashMap)getFilter(page,ordered,tags);
        Call<RestaurantsData> remoteData = createClient().getRestaurantsList(query);
        Log.e("connect", "url = " + remoteData.request().url().toString());
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag", "connect start = " + remoteData.request().url().toString());
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body());
                    data = response.body().getResults();
                    eListener.getList(data);
                } else {
                    Log.e("tag2", response.message() + call.request().body());
                    eListener.getList(null);
                    data = null;
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("onFailure", "request body =" + call.request().body());
                t.printStackTrace();
                data = null;
            }


        });

    }

    public void searchRestaurants(String search) {

        Call<RestaurantsData> remoteData = createClient().getSearchRestaurantsList(search);
        Log.e("tag", remoteData.request().url().toString());
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("respone", "success");
                    sListener.setResult(response.body().getResults());
                } else {
                    Log.e("respone", "fail");
                    List<Results> list = new ArrayList<Results>();
                    sListener.setResult(list);
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("connect", "failure");
                t.printStackTrace();
            }
        });
    }

    public void login(String name, String passwd) {

        final Call<Auth> remoteData =createClient().login(name, passwd);
        Log.e("tag", remoteData.request().url().toString());
        remoteData.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {


                if (response.isSuccessful()) {
                    Log.e("tag", response.message().toString());
                    //Log.e("tag",response.errorBody().toString());
                    //Log.e("tag",response.headers().toString());
                    lListener.setToken(response.body().getToken());
                } else {

                    Log.e("auth", "respone fail");
                    lListener.setToken("");
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Log.e("auth", "connect fail");
                lListener.setToken("");
            }
        });

    }

    private Map getFilter(String page,String ordered,String tags){
        Map<String, String> query = new HashMap<>();
        query.put("page", page);
        query.put("ordering", ordered);
        if (tags.length() > 0) {
            query.put("tags", tags);
        }
        return query;
    }
    public void getStoreList_withToken(String token, String page, String ordered, String tags) {

        String token_complete = make_token(token);

        HashMap<String,String> query = (HashMap)getFilter(page,ordered,tags);
        Call<RestaurantsData> remoteData =createClient().getRestaurantsList(token_complete, query);
        Log.e("connect", "url = " + remoteData.request().url().toString());
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag", "connect start");
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body().getResults().size());
                    data = response.body().getResults();
                    eListener.getList(data);
                } else {
                    Log.e("tag2", response.message() + call.request().body());
                    data = null;
                    eListener.getList(data);
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("onFailure", "request body =" + call.request().body());
                t.printStackTrace();
                data = null;
            }


        });
    }


    public void setRestaurantsLike(String token, String id) {
        String token_complete = make_token(token);

        final String r_id = id;
        Log.e("complete toekn", token_complete);
        Call<Favors> remoteData = createClient().setRestaurantsLike(token_complete, id);
        Log.e("connect", "url = " + remoteData.request().url().toString());
        remoteData.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call, Response<Favors> response) {
                if (response.isSuccessful()) {
                    Log.e("tag", "code = " + response.code());
                    Favors myfavor = response.body();
                    anInterface.getFavorite(r_id, myfavor.getId(), "true");
                } else {
                    Log.e("tag", "code = " + response.code());
                    Log.e("respone fail", response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setInterface(ActivityConnectInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void searchRestaurants_withToken(String keyWord, String token) {

        String token_complete = make_token(token);
        Call<RestaurantsData> remoteData = createClient().getSearchRestaurantsList_withToken(token_complete,keyWord);

        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("respone", "success");
                    sListener.setResult(response.body().getResults());
                } else {
                    Log.e("respone", "fail");
                    List<Results> list = new ArrayList<Results>();
                    sListener.setResult(list);
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.e("onFailure", "request body =" + call.request().body());
                t.printStackTrace();
                data = null;
            }
        });

    }

    private IRestaurantData createClient(){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return getService(client);
    }

    private String make_token(String token){
        String token_complete = "token " + token;
        return token_complete;
    }

    private IRestaurantData getService(Retrofit client){
        return client.create(IRestaurantData.class);
    }

    public void setRestaurantsDislike(String token, final String r_id, String f_id) {


        String token_complete = make_token(token);

        final String id = r_id;

        Call<String> remoteData = createClient().setRestaurantsDislike(token_complete, id, f_id);

        remoteData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("tag", "code = " + response.code());
                    if(anInterface !=null){
                        anInterface.getFavorite(id, "0", "false");
                    }
                    if(mpInterface !=null){
                        mpInterface.refresh(true);
                    }
                } else {
                    Log.e("tag", "code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void getMyFavorites(String token){

        String token_complete = make_token(token);
        Call<List<Results>> remoteData = createClient().getMyFavorites(token_complete);
        remoteData.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                if(response.isSuccessful()){
                    mpInterface.setData(response.body());
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


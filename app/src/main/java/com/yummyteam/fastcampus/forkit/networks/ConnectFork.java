package com.yummyteam.fastcampus.forkit.networks;

import android.util.Log;

import com.yummyteam.fastcampus.forkit.model.Auth;
import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
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

    private String mPage,mOrdered = "",mTags;
    public void getStoreList(String page,String ordered, String tags) {

        mPage = page;
        if(!mOrdered.contains(",-pk")){
            mOrdered = ordered+",-pk";
        }
        mTags = tags;
        HashMap<String,String> query = (HashMap)getFilter(mPage,mOrdered,mTags);
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

                getStoreList(mPage,mOrdered,mTags);
                data = null;
            }


        });

    }

    private String mSearch;

    public void searchRestaurants(String search) {
        mSearch = search;
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
                searchRestaurants(mSearch);
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
    private String mToken;
    public void getStoreList_withToken(String token, String page, String ordered, String tags) {

        String token_complete = make_token(token);
        mToken = token_complete;
        mPage = page;
        if(!mOrdered.contains(",-pk")){
            mOrdered = ordered+",-pk";
        }
        mTags = tags;
        HashMap<String,String> query = (HashMap)getFilter(mPage,mOrdered,mTags);
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
                getStoreList_withToken(mToken,mPage,mOrdered,mTags);
                data = null;
            }


        });
    }

    private String mId;
    public void setRestaurantsLike(String token, String id) {
        String token_complete = make_token(token);
        mToken = token_complete;
        mId = id;
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
                setRestaurantsLike(mToken,mId);
            }
        });
    }

    public void setInterface(ActivityConnectInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void searchRestaurants_withToken(String keyWord, String token) {

        String token_complete = make_token(token);
        Call<RestaurantsData> remoteData = createClient().getSearchRestaurantsList_withToken(token_complete,keyWord);
        mSearch = keyWord;
        mToken = token_complete;
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
                searchRestaurants_withToken(mSearch,mToken);
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

    private String mfId;
    public void setRestaurantsDislike(String token, final String r_id, String f_id) {


        String token_complete = make_token(token);

        final String id = r_id;
        mToken = token_complete;
        mId = r_id;
        mfId = f_id;

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
                setRestaurantsDislike(mToken,mId,mfId);
            }
        });

    }


    public void getMyFavorites(String token){

        String token_complete = make_token(token);
        mToken = token_complete;
        Call<List<Results>> remoteData = createClient().getMyFavorites(token_complete);
        remoteData.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                if(response.isSuccessful()){
                    mpInterface.setData_favors(response.body());
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                getMyFavorites(mToken);
                t.printStackTrace();
            }
        });
    }

    public void getMyReviews(String token) {
        String token_complete = make_token(token);
        mToken = token_complete;
        Call<List<Reviews>> remoteData = createClient().getMyReviews(token_complete);
        remoteData.enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                if(response.isSuccessful()){
                    mpInterface.setData_reviews(response.body());
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Reviews>> call, Throwable t) {
                getMyReviews(mToken);
                t.printStackTrace();
            }
        });
    }

    public void removeMyReviews(String token,String r_id,String id){
        String token_complete = make_token(token);
        mToken = token_complete;
        mId = r_id;
        mfId = id;
        Call<String> remoteData = createClient().removeMyReviews(token_complete,r_id,id);
        Log.e("url","url = "+remoteData.request().url());
        remoteData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    mpInterface.refresh(true);
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                removeMyReviews(mToken,mId,mfId);
            }
        });
    }

    String mTitle,mContents,mRate;
    public void modifiReview(String token,String r_id,String id, String title,String contents,String rate){
        mToken = make_token(token);
        mId = r_id;
        mfId = id;
        mTitle = title;
        mContents = contents;
        mRate = rate;
        HashMap<String,String> keys = new HashMap<>();
        keys.put("title",title);
        keys.put("content",contents);
        keys.put("score",rate);
        Call<Reviews> remoteData = createClient().modifiReview(mToken,r_id,id,keys);
        Log.e("tag",remoteData.request().url().toString());
        remoteData.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if(response.code() == 200){
                    mpInterface.refresh(true);
                }else{
                    Log.e("tag",response.code()+"");
                    mpInterface.refresh(true);

                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                modifiReview(mToken,mId,mfId,mTitle,mContents,mRate);
                t.printStackTrace();
            }
        });
    }
}


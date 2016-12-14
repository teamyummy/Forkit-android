package com.yummyteam.fastcampus.forkit.networks;

import android.content.Context;
import android.util.Log;

import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.model.reviewImagesResponse;
import com.yummyteam.fastcampus.forkit.view.detail.GetLikeId;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Favors favors;
    GetResultsInterface mListener;
    GetLikeId mListenr_like;
    Context context;
    String id;
    String MULTIPART_FORM_DATA="multipart/form-data";

    public ConnectFork2(GetResultsInterface mListener,GetLikeId mListenr_like) {
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
        Log.e("tag", "connect start");
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body());
                    data = response.body().getResults();
                    mListener.getList(data);
                } else {
                    Log.e("tag2", response.message() + call.request().body());
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

    public void getStoreDetail(String id) {

        String pk = id;
        String value = "restaurants";

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData2 service = client.create(IRestaurantData2.class);
        Call<Results> remoteData = service.getRestaurantsDetail(value, pk);
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag", "connect start");
        remoteData.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body());
                    detailData = response.body();
                    Log.e("Tag3", "detailData=" + detailData);
                    mListener.getDetail(detailData);
                } else {
                    Log.e("tag2", response.message() + call.request().body());
                    detailData = null;
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e("onFailure", "request body =" + call.request().body());
                t.printStackTrace();
                data = null;
            }


        });
    }
    public void getStoreDetailwithToken(String id,String token) {

        String pk = id;
        String value = "restaurants";
        final String realToken = "token " + token;

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData2 service = client.create(IRestaurantData2.class);
        Call<Results> remoteData = service.getRestaurantsDetailwithToken(realToken, pk);
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag", "connect start");
        remoteData.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body());
                    detailData = response.body();
                    Log.e("Tag3", "detailData=" + detailData);
                    mListener.getDetail(detailData);
                } else {
                    Log.e("tag2", response.message() + call.request().body());
                    detailData = null;
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e("onFailure", "request body =" + call.request().body());
                t.printStackTrace();
                data = null;
            }


        });
    }

    public void postReview(String token, String content, String score, final  List<String> filePath) {


        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final String realToken = "token " + token;
        String like=0+"";
        String disLike=0+"";
        Map<String, String> fieldMap = new HashMap<>();



        fieldMap.put("title", "title");
        fieldMap.put("content", content);
        fieldMap.put("score",score);
        fieldMap.put("like",like);
        fieldMap.put("disLike",disLike);

        String pk=1+"";

        IRestaurantData2 service = client.create(IRestaurantData2.class);
        Call<Reviews> call = service.postReviews(realToken,pk,fieldMap );
        Log.e("tag",call.request().url().toString());

        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {


                Log.i("Test code :",response.code()+"");
                if(!response.isSuccessful()){
                    Log.e("error",response.errorBody().byteStream().toString());
                }else{
                    Log.e("responseSuccess",response.body().toString());
                    Reviews reviews;
                    reviews= response.body();
                    String rvPk =reviews.getId();
                    Log.e("RES PK",rvPk);
                    postPhotos(filePath,realToken,rvPk);
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void postPhotos(List<String> filePath, String token,String rvPk){

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IRestaurantData2 service = client.create(IRestaurantData2.class);


        File file= new File(filePath.get(0));
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        final List<MultipartBody.Part> bodies=new ArrayList<>();

//        for(int i=0; i<filePath.size(); i++){
//            File file= new File(filePath.get(i));
//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            MultipartBody.Part body =
//                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
//
//            bodies.add(body);
//
//
//        }


        String altString = "Look so good";
        RequestBody alt =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), altString);

        String rtPk=1+"";


        Call<reviewImagesResponse> call = service.postPhotos(token,alt,rtPk,rvPk, body);
        call.enqueue(new Callback<reviewImagesResponse>() {
            @Override
            public void onResponse(Call<reviewImagesResponse> call,
                                   Response<reviewImagesResponse> response) {
                Log.v("Upload", "success");
                Log.v("Phtos",response.code()+"");
                Log.e("URL", call.request().url().toString());
//                Log.v("phtos",response.body().toString());
            }

            @Override
            public void onFailure(Call<reviewImagesResponse> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

    }

    public void putLikeRestau(String token,String id){

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;



        Call<Favors> call = service.putLikeRestaurant(realToken,id);
        call.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call,
                                   Response<Favors> response) {
                Log.e("http",response.code()+"");
                Log.e("response",response.body().toString());


            }

            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void deleteLike(String token,String rtId){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;

        Call<Favors> call = service.deleteLikeRestaurant(realToken,rtId);
        call.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call,
                                   Response<Favors> response) {
                Log.e("http",response.code()+"");
            }
            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    public void getLike(String token,String rtId){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;

        Call<Favors> call = service.getLikeId(realToken,rtId);
        call.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call,
                                   Response<Favors> response) {
                Log.e("http",response.code()+"");
                favors=response.body();
                mListenr_like.getLikeList(favors);

            }
            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    public void postLike(String token,String rtId, String rvId,String like){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;

        Call<Favors> call = service.postLikeReview(realToken,rtId,rvId,like);
        call.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call,
                                   Response<Favors> response) {
                Log.e("http",response.code()+"");


            }
            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
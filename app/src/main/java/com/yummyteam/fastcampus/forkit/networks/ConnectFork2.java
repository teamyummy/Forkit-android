package com.yummyteam.fastcampus.forkit.networks;

import android.content.Context;
import android.util.Log;

import com.darsh.multipleimageselect.models.Image;
import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.RestaurantsData;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.ReviewLike;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.model.ReviewImagesResponse;
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
    GetResultsInterface mListener;


    Context context;
    String id;
    String MULTIPART_FORM_DATA="multipart/form-data";

    int i;

    public ConnectFork2(GetResultsInterface mListener) {
        this.mListener = mListener;
    }


    public void getStoreList(int intPage) {

        Map<String, String> query = new HashMap<>();

        String page =intPage+"";
        query.put("page", page);


        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Retrofit client에서 사용할 interface
        IRestaurantData2 service = client.create(IRestaurantData2.class);
        String value = "restaurants";

        Call<RestaurantsData> remoteData = service.getRestaurantsList(query);
        // 4. 비동기 데이터를 받기 위한 리스너 세팅
        Log.e("tag", "connect start");
        remoteData.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                if (response.isSuccessful()) {
                    Log.e("tag1", "size = " + response.body());
                    data = response.body().getResults();

//                    while(response.body().getNext()!=null){
//                        i=1;
//                        i++;
//                        getStoreList(i);
//
//
//                    }
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
    public void refreshMyLikeReview(String id,String token) {

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
                    mListener.getMyLikeReview(detailData);
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



    public void postReview(final String token,String id,String title, String content, String score, final ArrayList<Image> filePaths) {


        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final String realToken = "token " + token;
        String like=0+"";
        String disLike=0+"";
        Map<String, String> fieldMap = new HashMap<>();




        fieldMap.put("title", title);
        fieldMap.put("content", content);
        fieldMap.put("score",score);
        fieldMap.put("like",like);
        fieldMap.put("disLike",disLike);



        IRestaurantData2 service = client.create(IRestaurantData2.class);
        Call<Reviews> call = service.postReviews(realToken,id,fieldMap );
        Log.e("tag",call.request().url().toString());

        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {


                Log.i("Test code :",response.code()+"");
                if(!response.isSuccessful()){
                    Log.e("error",response.errorBody().byteStream().toString());
                }else{
                    Log.e("responseSuccess",response.body().toString());
                    Log.v("Post Review",response.code()+"");
                    Reviews reviews;
                    reviews= response.body();
                    String rvPk =reviews.getId();
                    Log.e("RES PK",rvPk);
                    for(int i=0;i<filePaths.size();i++){
                        String filePath =filePaths.get(i).path;
                        postPhotos(realToken,rvPk,filePath);

                    }


                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void postPhotos(String token,String rvPk,String path){

        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IRestaurantData2 service = client.create(IRestaurantData2.class);



        File file= new File(path);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        String altString = "Look so good";

        RequestBody alt =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), altString);

        String rtPk=1+"";


        Call<ReviewImagesResponse> call = service.postPhotos(token,alt,rtPk,rvPk, body);
        call.enqueue(new Callback<ReviewImagesResponse>() {
            @Override
            public void onResponse(Call<ReviewImagesResponse> call,
                                   Response<ReviewImagesResponse> response) {
                Log.v("Upload", "success");
                Log.v("Phtos",response.code()+"");
                Log.e("URL", call.request().url().toString());
//                Log.v("phtos",response.body().toString());
            }

            @Override
            public void onFailure(Call<ReviewImagesResponse> call, Throwable t) {
                t.printStackTrace();
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
                Log.e("putlikeRestau_http",response.code()+"");

                Favors favors=response.body();
                mListener.getLikeList(favors.getId());

            }

            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void deleteLikeRestau(String token,String rtId,String rvId){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;

        Call<Favors> call = service.deleteLikeRestaurant(realToken,rtId,rvId);
        call.enqueue(new Callback<Favors>() {
            @Override
            public void onResponse(Call<Favors> call,
                                   Response<Favors> response) {
                Log.e("DeleteLikeRestau_http",response.code()+"");

            }
            @Override
            public void onFailure(Call<Favors> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void postLike(String token, String rtId, final String rvId, final String like, final int position){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);


        final String realToken = "token " + token;
        Log.e("PostLIke LIKE",like);
        Log.e("PostLIke TOKEN",realToken);
        Log.e("PostLIke rtId,rvId",rtId+" "+rvId);
        Call<ReviewLike> call = service.postLikeReview(realToken,rtId,rvId,like);
        call.enqueue(new Callback<ReviewLike>() {
            @Override
            public void onResponse(Call<ReviewLike> call,
                                   Response<ReviewLike> response) {
                Log.e("PostLikeReview_http",response.code()+"");
                Log.e("URL1", call.request().url().toString());
                mListener.refresh(response.body().getId(),position);

                //ReviewLike reviewLike=response.body();
                //mListener.getPostReview(reviewLike.getId().toString());

                Log.e("URL2", call.request().url().toString());



            }
            @Override
            public void onFailure(Call<ReviewLike> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    public void putLike(String token,String rtId, String rvId,String lkId,String like){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;
        Log.e("-------lkID",lkId);


        Call<ReviewLike> call = service.putLikeReview(realToken,rtId,rvId,lkId,like);
        call.enqueue(new Callback<ReviewLike>() {
            @Override
            public void onResponse(Call<ReviewLike> call,
                                   Response<ReviewLike> response) {
                Log.e("putLikeReview_http",response.code()+"");
                Log.e("URL", call.request().url().toString());

            }
            @Override
            public void onFailure(Call<ReviewLike> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void deleteLike(final String token, final String rtId, final String rvId, final String lkId, final String like, final int position){
        Retrofit client = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRestaurantData2 service = client.create(IRestaurantData2.class);

        final String realToken = "token " + token;
        Log.e("-------lkID",lkId);


        Call<ReviewLike> call = service.deleteLikeReview(realToken,rtId,rvId,lkId);
        call.enqueue(new Callback<ReviewLike>() {
            @Override
            public void onResponse(Call<ReviewLike> call,
                                   Response<ReviewLike> response) {
                Log.e("deleteLikeReview_http",response.code()+"");
                Log.e("URL", call.request().url().toString());
                postLike(token,rtId,rvId,like,position);

            }
            @Override
            public void onFailure(Call<ReviewLike> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }


}
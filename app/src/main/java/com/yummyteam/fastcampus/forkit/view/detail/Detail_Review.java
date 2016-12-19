package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.util.ArrayList;
import java.util.List;

public class Detail_Review extends AppCompatActivity implements GetResultsInterface {
    private RecyclerView recyclerView_Review;
    private Toolbar toolbar;
    private ImageButton ib_back_toolbar;
    private ReviewDetailAdapter reviewAdapter;
    private Results data;
    private String token;
    private String id;
    ConnectFork2 connectFork;

    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__review);

        pb=(ProgressBar)findViewById(R.id.progressBar2);
        pb.setVisibility(View.VISIBLE);

        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        id= bundle.getString("restaurant_id");
        token=bundle.getString("token");

        connectFork = new ConnectFork2(this);
        checkToken();

        reviewAdapter= new ReviewDetailAdapter(R.layout.item_review,this);
        reviewAdapter.setLikeInterface(this);
        recyclerView_Review=(RecyclerView)findViewById(R.id.recyclerview_review);
        recyclerView_Review.setAdapter(reviewAdapter);
        RecyclerView.LayoutManager manager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_Review.setLayoutManager(manager2);

        toolbar = (Toolbar)findViewById(R.id.toolBar_sub);
        ib_back_toolbar=(ImageButton)findViewById(R.id.ib_back_toolbar);
        ib_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail_Review.this.finish();
            }

        });

    }
    public void checkToken(){
        if(token.equals("")){

            connectFork.getStoreDetail(id);

        }else{

            connectFork.getStoreDetailwithToken(id,token);

        }
    }
    @Override
    public void getList(List<Results> data) {

    }

    @Override
    public void getDetail(Results data) {
        pb.setVisibility(View.GONE);
        this.data=data;
        reviewAdapter.addReviewData((ArrayList<Reviews>) data.getReviews());
    }
    @Override
    public void getLikeList(String f_id) {

    }
    @Override
    public void setReviewLike(String myLike, String reviewId, Boolean existId, String lkId, Boolean changed) {

    }
    @Override
    public void getMyLikeReview(Results data) {
    }

}


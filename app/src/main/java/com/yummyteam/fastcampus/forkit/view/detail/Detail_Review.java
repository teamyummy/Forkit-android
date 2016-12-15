package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.util.ArrayList;
import java.util.List;

public class Detail_Review extends AppCompatActivity implements GetResultsInterface {
    RecyclerView recyclerView_Review;
    Toolbar toolbar;
    ImageButton ib_back_toolbar;
    ReviewAdapter reviewAdapter;
    Results data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__review);

        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        String id= bundle.getString("restaurant_id");

        ConnectFork2 connectFork = new ConnectFork2(this);
        connectFork.getStoreDetail(id);

        reviewAdapter= new ReviewAdapter(R.layout.item_review,this);
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


    @Override
    public void getList(List<Results> data) {

    }

    @Override
    public void getDetail(Results data) {

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
    public void getPostReview(String s) {

    }


}


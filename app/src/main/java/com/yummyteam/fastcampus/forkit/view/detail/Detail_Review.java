package com.yummyteam.fastcampus.forkit.view.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yummyteam.fastcampus.forkit.R;

public class Detail_Review extends AppCompatActivity {
    RecyclerView recyclerView_Review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__review);

        ReviewAdapter reviewAdapter= new ReviewAdapter(null,R.layout.item_review,this);
        recyclerView_Review=(RecyclerView)findViewById(R.id.recyclerview_review);
        recyclerView_Review.setAdapter(reviewAdapter);
        RecyclerView.LayoutManager manager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_Review.setLayoutManager(manager2);
    }

}


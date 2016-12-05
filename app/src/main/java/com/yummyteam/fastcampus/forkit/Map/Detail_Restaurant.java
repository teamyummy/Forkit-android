package com.yummyteam.fastcampus.forkit.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yummyteam.fastcampus.forkit.R;

public class Detail_Restaurant extends AppCompatActivity {


    ListView listView_menu;
    RecyclerView recyclerView1;
    RecyclerView recyclerView_Review;

    ImageView igCall;
    ImageView igNavi;
    ImageView igPostReview;

    TextView tvDetailMenu;
    TextView tvDetailReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);


        PictureAdapter pictureAdapter =new PictureAdapter(null,R.layout.item_picture_maptodetails,this);
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerview1);
        recyclerView1.setAdapter(pictureAdapter);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(manager);

        ReviewAdapter reviewAdapter= new ReviewAdapter(null,R.layout.item_review,this);
        recyclerView_Review=(RecyclerView)findViewById(R.id.recyclerview_review);
        recyclerView_Review.setAdapter(reviewAdapter);
        RecyclerView.LayoutManager manager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_Review.setLayoutManager(manager2);

        tvDetailReview=(TextView)findViewById(R.id.tvDetail_Review);
        tvDetailReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDetailReview();
            }
        });

        listView_menu = (ListView) findViewById(R.id.listView_menu);
        setList_Menu();
        tvDetailMenu =(TextView)findViewById(R.id.tvDetailMenu);
        tvDetailMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDetailMenu();
            }
        });

        igCall=(ImageView)findViewById(R.id.igCall);
        igCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01027249108"));
                startActivity(intent);

            }
        });
        igNavi=(ImageView)findViewById(R.id.igNavi);
        igNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goShowLocation();

            }
        });

        igPostReview=(ImageView)findViewById(R.id.igPostReview);
        igPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                PostReviewFragment postReviewFragment = new PostReviewFragment();
                postReviewFragment.show(fm, "PostReviewFragment");
            }
        });

    }


    public void goDetailReview(){
        Intent intent = new Intent(this,Detail_Review.class);
        startActivity(intent);
    }
    public void goDetailMenu(){
        Intent intent =new Intent(this,Detail_menu.class);
        startActivity(intent);
    }
    public void goShowLocation (){
        Intent intent = new Intent(Detail_Restaurant.this,ShowLocationActivity.class);
        intent.putExtra("Res_lat",37.515364);
        intent.putExtra("Res_lon",127.022796);
        startActivity(intent);
    }

    public void setList_Menu(){
        String[] datas = {"Sandwich", "Awesome Sandwich", "Hot Sandwich", "Coke"};

        ArrayAdapter adapter = new ArrayAdapter(         // 인자 전달
                this,                               // 컨택스트
                android.R.layout.simple_list_item_1,// 아이템 레이아웃
                datas                               // 데이터
        );

        listView_menu = (ListView) findViewById(R.id.listView_menu);
        listView_menu.setAdapter(adapter);

    }

}

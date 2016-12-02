package com.yummyteam.fastcampus.forkit.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yummyteam.fastcampus.forkit.R;

public class mapToDetails extends AppCompatActivity {


    ListView listView_menu;
    RecyclerView recyclerView1;
    RecyclerView recyclerView_Review;


    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_to_details);


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

        listView_menu = (ListView) findViewById(R.id.listView_menu);
        setDatas();



    }

    public void setDatas(){
        String datas[] = {"Sandwich", "Awesome Sandwich", "Hot Sandwich", "Coke"};

        adapter = new ArrayAdapter<String>(         // 인자 전달
                this,                               // 컨택스트
                android.R.layout.simple_list_item_1,// 아이템 레이아웃
                datas                               // 데이터
        );

        listView_menu = (ListView) findViewById(R.id.listView_menu);
        listView_menu.setAdapter(adapter);

    }

}

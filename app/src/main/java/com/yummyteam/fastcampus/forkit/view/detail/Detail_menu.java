package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detail_menu extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> menus;
    private ArrayList<String> price;
    private SimpleAdapter simpleAdapter;

    private ArrayList<Map<String,String>> datas;

    private Toolbar toolbar;
    private ImageButton ib_back_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        toolbar = (Toolbar)findViewById(R.id.toolBar_sub);
        ib_back_toolbar=(ImageButton)findViewById(R.id.ib_back_toolbar);
        ib_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail_menu.this.finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);


        setList_Menu();

    }

    private void setList_Menu(){
        datas=new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        menus=bundle.getStringArrayList("menu");
        price=bundle.getStringArrayList("price");

        for(int i=0; i<menus.size()/2; i++){

            Map<String,String> menuMap=new HashMap<>();

            menuMap.put("menu",menus.get(i));
            menuMap.put("price",price.get(i)+"원");
            Log.e("price",price.get(i));

            datas.add(menuMap);

        }


        simpleAdapter = new SimpleAdapter(
                this,
                datas,
                R.layout.item_menu,
                new String[]{"menu","price"},
                new int[]{R.id.tvMenu, R.id.tvPrice}
        );



        listView.setAdapter(simpleAdapter);

    }

}

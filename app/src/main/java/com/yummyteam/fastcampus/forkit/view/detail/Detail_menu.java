package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;

public class Detail_menu extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datas;

    Toolbar toolbar;
    ImageButton ib_back_toolbar;
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


        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        datas=bundle.getStringArrayList("menu");

        setList_Menu();

    }

    public void setList_Menu(){

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                datas
        );

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

}

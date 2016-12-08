package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;

public class Detail_menu extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        datas=bundle.getStringArrayList("menu");

        setList_Menu();

    }

    public void setList_Menu(){

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.simpleitem,
                datas
        );

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

}

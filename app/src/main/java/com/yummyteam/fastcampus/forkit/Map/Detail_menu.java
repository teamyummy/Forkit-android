package com.yummyteam.fastcampus.forkit.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yummyteam.fastcampus.forkit.R;

public class Detail_menu extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        setList_Menu();

    }

    public void setList_Menu(){
        String[] datas = {"Sandwich", "Awesome Sandwich", "Hot Sandwich", "Coke"};

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.simpleitem,
                datas
        );

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

}

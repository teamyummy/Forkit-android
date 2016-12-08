package com.yummyteam.fastcampus.forkit.view.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.ELAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchRestaurants extends AppCompatActivity implements View.OnClickListener, SearchInterface {
    private ImageButton ib_back_toolbar;
    private TextView noSearch_content;
    private EditText et_search;
    private String keyWord ="";
    private ConnectFork connect;
    private RecyclerView searchedList;
    private ELAdapter elAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurants);
        init();



    }

    private void init() {
        ib_back_toolbar = (ImageButton)findViewById(R.id.ib_back_toolbar);
        noSearch_content = (TextView)findViewById(R.id.noSearch_content);
        et_search = (EditText)findViewById(R.id.et_search);
        SearchEditorActionListener listener = new SearchEditorActionListener(this);
        et_search.setOnEditorActionListener(listener);
        ib_back_toolbar.setOnClickListener(this);
        connect  = new ConnectFork(this);
        searchedList = (RecyclerView)findViewById(R.id.searched_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        searchedList.setLayoutManager(manager);
        elAdapter = new ELAdapter(this);
        searchedList.setAdapter(elAdapter);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ib_back_toolbar)
        {
            finish();
        }
    }

    @Override
    public void getKeyWord(String keyWord) {
        this.keyWord = keyWord;
        Log.e("getKeyWord","keyword = " + this.keyWord);
        connect.searchRestaurants(this.keyWord);
    }

    @Override
    public void setResult(List<Results> results) {
        if(results.size() ==0){
            Log.e("getKeyWord","result size = 0");
            noSearch_content.setVisibility(View.VISIBLE);
        }else{
            elAdapter.removeallData();
            Log.e("getKeyWord","size is not null"+results.size());
            elAdapter.addDatas((ArrayList)results);
            noSearch_content.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAlert() {
        Toast.makeText(getBaseContext(),"내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
    }
}

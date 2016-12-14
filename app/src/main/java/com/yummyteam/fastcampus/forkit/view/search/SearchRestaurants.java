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
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.main.ActivityConnectInterface;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.ELAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchRestaurants extends AppCompatActivity implements View.OnClickListener, SearchInterface,ActivityConnectInterface {
    private ImageButton ib_back_toolbar;
    private TextView noSearch_content;
    private EditText et_search;
    private String keyWord ="";
    private ConnectFork connect;
    private RecyclerView searchedList;
    private ELAdapter elAdapter;
    private TokenCache cache;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurants);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void init() throws IOException {
        ib_back_toolbar = (ImageButton)findViewById(R.id.ib_back_toolbar);
        noSearch_content = (TextView)findViewById(R.id.noSearch_content);
        et_search = (EditText)findViewById(R.id.et_search);
        SearchEditorActionListener listener = new SearchEditorActionListener(this);
        et_search.setOnEditorActionListener(listener);
        ib_back_toolbar.setOnClickListener(this);
        connect  = new ConnectFork((SearchInterface) this);
        connect.setInterface((ActivityConnectInterface)this);
        searchedList = (RecyclerView)findViewById(R.id.searched_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        searchedList.setLayoutManager(manager);
        elAdapter = new ELAdapter();
        elAdapter.setInterface(this);
        searchedList.setAdapter(elAdapter);
        cache = TokenCache.getInstance();
        token = cache.read();

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
        if(token.equals("")) {
            connect.searchRestaurants(this.keyWord);
        }else{
            connect.searchRestaurants_withToken(this.keyWord,token);
        }
    }

    @Override
    public void setResult(List<Results> results) {
        elAdapter.removeallData();
        if(results.size() ==0){

            Log.e("getKeyWord","result size = 0");
            noSearch_content.setVisibility(View.VISIBLE);
        }else{
            Log.e("getKeyWord","size is not null"+results.size());
            elAdapter.addDatas((ArrayList)results);
            noSearch_content.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAlert() {
        Toast.makeText(getBaseContext(),"내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setFavorite(String r_id, String like, String f_id) {
        connect.setRestaurantsLike(token,r_id);
    }

    @Override
    public void getFavorite(String r_id, String f_id, String like) {
        elAdapter.changeMyLike(r_id,f_id,like);
    }

    @Override
    public void refresh() {

    }
}

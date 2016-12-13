package com.yummyteam.fastcampus.forkit.view.detail;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Images;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.util.ArrayList;
import java.util.List;

public class Detail_Restaurant extends AppCompatActivity implements GetResultsInterface {


    ListView listView_menu;
    RecyclerView recyclerView1;
    RecyclerView recyclerView_Review;

    ImageView igCall;
    ImageView igNavi;
    ImageView igPostReview;
    ImageView imageLike;
    ImageView selectedLikeImage;

    Boolean isSelected;

    TextView tvDetailMenu;
    TextView tvDetailReview;
    TextView tvStoreName;
    TextView tvRating;
    TextView tvBookMark;
    TextView tvReview;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvParking;
    TextView tvHours;

    Results data;
    ArrayList<String> datas;

    ArrayAdapter arrayAdapter;
    ImageButton ib_back_toolbar;
    Toolbar toolbar;

    PictureAdapter pictureAdapter;
    ReviewAdapter reviewAdapter;

    String id;
    String phoneN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        phoneN="";
        datas = new ArrayList<>();

        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        id= bundle.getString("restaurant_id");


        ConnectFork2 connectFork = new ConnectFork2(this);
        connectFork.getStoreDetail(id);

        topPictureList();
        reviewList();
        setList_Menu();

        toolbar = (Toolbar)findViewById(R.id.toolBar_sub);
        ib_back_toolbar=(ImageButton)findViewById(R.id.ib_back_toolbar);
        ib_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail_Restaurant.this.finish();
            }
        });


        tvStoreName=(TextView)findViewById(R.id.tvStoreName);
        tvRating=(TextView)findViewById(R.id.tvRating);
        tvBookMark=(TextView)findViewById(R.id.tvBookMark);
        tvReview=(TextView)findViewById(R.id.tvReview);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        tvParking=(TextView)findViewById(R.id.tvParking);
        tvHours=(TextView)findViewById(R.id.tvHours);
        listView_menu = (ListView) findViewById(R.id.listView_menu);
        imageLike=(ImageView)findViewById(R.id.imageLike);


        tvDetailReview=(TextView)findViewById(R.id.tvDetail_Review);
        tvDetailReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_Restaurant.this,Detail_Review.class);
                intent.putExtra("restaurant_id",id);
                goDetailReview();

            }
        });


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

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneN));
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

        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedLikeImage != null){
                    selectLike(false);
                }else{
                    selectLike(true);
                }
            }
        });

    }

    public void setDatas(){

        tvStoreName.setText(data.getName());

        tvRating.setText("평점  ㅣ  "+data.getReview_score());
        tvBookMark.setText("즐겨찾기  ㅣ  "+data.getTotal_like());
        tvReview.setText("Review  ㅣ  "+data.getReview_count());
        tvAddress.setText(data.getAddress());
        tvPhone.setText(data.getPhone());
        tvHours.setText(data.getOperation_hour());
        tvParking.setText(data.getDesc_parking());
        phoneN=data.getPhone();
    }

    public void topPictureList(){
        pictureAdapter =new PictureAdapter(R.layout.item_picture_maptodetails,this);
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerview1);
        recyclerView1.setAdapter(pictureAdapter);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(manager);
    }
    public void reviewList(){
        reviewAdapter= new ReviewAdapter(R.layout.item_review,this);
        recyclerView_Review=(RecyclerView)findViewById(R.id.recyclerview_review);
        recyclerView_Review.setAdapter(reviewAdapter);
        RecyclerView.LayoutManager manager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_Review.setLayoutManager(manager2);
    }
    float scale_x=1;
    float scale_y=1;
    public void biggerButton(){
        scale_x=scale_x*1.25f;
        scale_y=scale_y*1.25f;

        ObjectAnimator ani1 = ObjectAnimator.ofFloat(imageLike, "scaleX", scale_x);
        ObjectAnimator ani2 = ObjectAnimator.ofFloat(imageLike, "scaleY", scale_y);//배수

        AnimatorSet aniset= new AnimatorSet();
        aniset.setDuration(200);
        aniset.playTogether(ani1,ani2);
        aniset.start();

    }

    public ImageView selectLike(Boolean isSelected){

        if(isSelected){

            scale_x=scale_x*0.8f;
            scale_y=scale_y*0.8f;

            ObjectAnimator ani1 = ObjectAnimator.ofFloat(imageLike, "scaleX", scale_x);
            ObjectAnimator ani2 = ObjectAnimator.ofFloat(imageLike, "scaleY", scale_y);//배수

            AnimatorSet aniset= new AnimatorSet();
            aniset.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    biggerButton();
                    imageLike.setImageResource(R.drawable.btn_common_card_like_pressed);
                    selectedLikeImage=imageLike;

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            aniset.setDuration(200);
            aniset.playTogether(ani1,ani2);
            aniset.start();


        }else{
            scale_x=scale_x*0.8f;
            scale_y=scale_y*0.8f;

            ObjectAnimator ani1 = ObjectAnimator.ofFloat(imageLike, "scaleX", scale_x);
            ObjectAnimator ani2 = ObjectAnimator.ofFloat(imageLike, "scaleY", scale_y);//배수

            AnimatorSet aniset= new AnimatorSet();
            aniset.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    biggerButton();
                    imageLike.setImageResource(R.drawable.btn_unpress_like);
                    selectedLikeImage= null;

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            aniset.setDuration(200);
            aniset.playTogether(ani1,ani2);
            aniset.start();


        }

        return imageLike;
    }

    public void goDetailReview(){
        Intent intent = new Intent(this,Detail_Review.class);
        intent.putExtra("restaurant_id",id);
        startActivity(intent);
    }
    public void goDetailMenu(){
        Intent intent =new Intent(this,Detail_menu.class);
        intent.putExtra("menu",datas);
        startActivity(intent);
    }
    public void goShowLocation (){
        Intent intent = new Intent(Detail_Restaurant.this,ShowLocationActivity.class);
        intent.putExtra("Res_lat", Double.parseDouble(data.getLatitude()));
        intent.putExtra("Res_lon", Double.parseDouble(data.getLongitude()));
        startActivity(intent);
    }

    public void setList_Menu(){
        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                datas
        );
        listView_menu = (ListView) findViewById(R.id.listView_menu);
        listView_menu.setAdapter(arrayAdapter);

    }
    public void addMenuData(Results data){
        int size=data.getMenus().size();

        for (int i = 0; i < size; i++) {
            String menu = data.getMenus().get(i).getName();

            datas.add(menu);
        }
        arrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void getList(List<Results> data) {


    }

    @Override
    public void getDetail(Results data) {
        this.data=data;
        Log.e("DataTag",this.data.getName());
        setDatas();
        addMenuData(this.data);
        pictureAdapter.addImageData((ArrayList<Images>) data.getImages());
        reviewAdapter.addReviewData((ArrayList<Reviews>) data.getReviews());
    }
}

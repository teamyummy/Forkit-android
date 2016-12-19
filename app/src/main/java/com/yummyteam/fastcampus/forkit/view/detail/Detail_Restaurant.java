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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Favors;
import com.yummyteam.fastcampus.forkit.model.Images;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Detail_Restaurant extends AppCompatActivity implements GetResultsInterface,PostReviewFragment.PostListner {


    private ListView listView_menu;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView_Review;

    private ImageView igCall;
    private ImageView igNavi;
    private ImageView igPostReview;
    private ImageView imageLike;
    private ImageView selectedLikeImage;

    private Boolean isSelected;

    private TextView tvDetailMenu;
    private TextView tvDetailReview;
    private TextView tvStoreName;
    private TextView tvRating;
    private TextView tvBookMark;
    private TextView tvReview;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvParking;
    private TextView tvHours;

    private String lk;
    private Results data;
    private ArrayList<Map<String,String>> datas;
    private ArrayList<String> menuData;
    private ArrayList<String> priceData;


    private SimpleAdapter simpleAdapter;
    private ImageButton ib_back_toolbar;
    Toolbar toolbar;

    private PictureAdapter pictureAdapter;
    private ReviewAdapter reviewAdapter;

    private String id;
    private String phoneN;

    private TokenCache cache;
    private String token;

    private ConnectFork2 connectFork;
    Favors favors;

    private Boolean changedLike;

    private int count;

    private ProgressBar pb;

    public Detail_Restaurant(){

        cache = TokenCache.getInstance();
        data = new Results();
        menuData=new ArrayList<>();
        priceData=new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        phoneN="";
        pb=(ProgressBar)findViewById(R.id.progressBar);
        //pb.setVisibility(View.VISIBLE);


        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        id= bundle.getString("restaurant_id");


        connectFork = new ConnectFork2(this);
        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkToken();


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

                if(token.equals("")){

                    Toast.makeText(Detail_Restaurant.this,"로그인이 필요합니다",Toast.LENGTH_LONG).show();

                }else{
                    FragmentManager fm = getSupportFragmentManager();
                    PostReviewFragment postReviewFragment = new PostReviewFragment(id);

                    postReviewFragment.show(fm, "PostReviewFragment");

                }

            }
        });

        count=0;
        changedLike=false;
        imageLike.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                if(selectedLikeImage != null){
                    selectLike(false);
                    data.setMy_like("false");



                }else{
                    if(token!=null){
                        selectLike(true);
                        data.setMy_like("true");

                    }else{
                        Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_LONG).show();
                    }
                }
                count++;
                if(count%2==0) {
                    changedLike=false;
                }else{
                    changedLike=true;
                }
                Log.e("changedLike",changedLike+"");

                lk=data.getMy_like_id();



            }
        });

        likeItem.setChanged(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
     checkToken();

    }


    public void checkToken(){
        if(token.equals("")){

            connectFork.getStoreDetail(id);

        }else{

            connectFork.getStoreDetailwithToken(id,token);

        }
    }
    public void setDefaultLikeImg(){
        if(token!=null){
            if(data.getMy_like().equals("true")){
                selectLike(true);
            }else if(data.getMy_like().equals("false")){
                selectLike(false);
            }

        }else{
            selectLike(false);
        }


    }

    public void setDatas(){

        tvStoreName.setText(data.getName());

        tvRating.setText("평점  "+data.getReview_score());
        tvBookMark.setText("즐겨찾기  "+data.getTotal_like());
        tvReview.setText("Review  "+data.getReview_count());
        tvAddress.setText(data.getAddress());
        tvPhone.setText(data.getPhone());
        tvHours.setText(data.getOperation_hour());
        tvParking.setText(data.getDesc_parking());
        phoneN=data.getPhone();
    }

    public void topPictureList(){
        pictureAdapter =new PictureAdapter(R.layout.item_picture_drestautop,this);
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerview1);
        recyclerView1.setAdapter(pictureAdapter);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(manager);
    }
    public void reviewList(){
        reviewAdapter= new ReviewAdapter(R.layout.item_review,this);
        recyclerView_Review=(RecyclerView)findViewById(R.id.recyclerview_review);
        recyclerView_Review.setAdapter(reviewAdapter);
        reviewAdapter.setLikeInterface(this);
        RecyclerView.LayoutManager manager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_Review.setLayoutManager(manager2);
    }
    private float scale_x=1;
    private float scale_y=1;
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
        intent.putExtra("token",token);
        startActivity(intent);
    }
    public void goDetailMenu(){
        Intent intent =new Intent(this,Detail_menu.class);
        intent.putExtra("menu",menuData);
        intent.putExtra("price",priceData);
        startActivity(intent);
    }
    public void goShowLocation (){
        pb.setVisibility(View.VISIBLE);
        Intent intent = new Intent(Detail_Restaurant.this,ShowLocationActivity.class);
        intent.putExtra("Res_lat", Double.parseDouble(data.getLatitude()));
        intent.putExtra("Res_lon", Double.parseDouble(data.getLongitude()));
        startActivity(intent);
    }

    public void setList_Menu(){

        datas = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(
                this,
                datas,
                R.layout.item_menu,
            new String[]{"menu","price"},
                new int[]{R.id.tvMenu, R.id.tvPrice}
        );
        listView_menu = (ListView) findViewById(R.id.listView_menu);
        listView_menu.setAdapter(simpleAdapter);

    }
    public void addMenuData(Results data) {
        int size = data.getMenus().size();


        for (int i = 0; i < size; i++) {

            Map<String, String> map = new HashMap();
            String menu = data.getMenus().get(i).getName();
            String price = data.getMenus().get(i).getPrice();

            map.put("menu", menu);
            map.put("price", price);

            datas.add(map);

            menuData.add(menu);
            priceData.add(price);

        }
        simpleAdapter.notifyDataSetChanged();

    }


    @Override
    public void getList(List<Results> data) {


    }

    @Override
    public void getDetail(Results data) {


        this.data=data;
        Log.e("DataTag",this.data.getName());
        setDatas();
        addMenuData(data);
        pictureAdapter.addImageData((ArrayList<Images>) data.getImages());
        reviewAdapter.addReviewData((ArrayList<Reviews>) data.getReviews());
        setDefaultLikeImg();
        pb.setVisibility(View.GONE);


    }

    @Override
    public void getLikeList(String f_id) {
        lk=f_id;
    }


    LikeItem likeItem = new LikeItem();

    @Override
    protected void onPause() {
        super.onPause();
        pb.setVisibility(View.GONE);


        if (changedLike) {
            if (data.getMy_like().equals("true")) {
                connectFork.putLikeRestau(token, data.getId());
            } else if (data.getMy_like().equals("false")) {
                connectFork.deleteLikeRestau(token, data.getId(), lk);
            }
        }

        if(likeItem.getChanged()){
            connectFork.deleteLike(token, data.getId(), likeItem.getReviewId(), likeItem.getLkId(), likeItem.getMyLike());
            //connectFork.postLike(token, data.getId(), likeItem.getReviewId(), likeItem.getMyLike());
        }


    }


    @Override
    public void setReviewLike(String myLike, String reviewId, Boolean existId, String lkId, Boolean changed) {

        likeItem.setExistId(existId);
        likeItem.setLkId(lkId);
        likeItem.setMyLike(myLike);
        likeItem.setReviewId(reviewId);
        likeItem.setChanged(changed);



    }

    @Override
    public void getMyLikeReview(Results data) {

        reviewAdapter.addReviewData((ArrayList<Reviews>) data.getReviews());
    }

    @Override
    public void refresh() {

    }





}
class LikeItem{
    String myLike;
    String reviewId;

    public ArrayList<Boolean> getCheckExist() {
        return checkExist;
    }

    public void setCheckExist(ArrayList<Boolean> checkExist) {
        this.checkExist = checkExist;
    }

    ArrayList<Boolean> checkExist;

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    Boolean changed;

    public String getLkId() {
        return lkId;
    }

    public void setLkId(String lkId) {
        this.lkId = lkId;
    }

    public String getMyLike() {
        return myLike;
    }

    public void setMyLike(String myLike) {
        this.myLike = myLike;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Boolean getExistId() {
        return existId;
    }

    public void setExistId(Boolean existId) {
        this.existId = existId;
    }

    Boolean existId;
    String lkId;

}
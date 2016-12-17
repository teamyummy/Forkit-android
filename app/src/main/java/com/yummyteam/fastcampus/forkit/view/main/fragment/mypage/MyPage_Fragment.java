package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.login.LoginActivity;
import com.yummyteam.fastcampus.forkit.view.main.ActivityConnectInterface;
import com.yummyteam.fastcampus.forkit.view.main.MainViewInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MyPage_Fragment extends Fragment implements View.OnClickListener, MyPageInterface {

    private TokenCache cache;
    private String token;
    private ActivityConnectInterface anInterface;

    public MyPage_Fragment() {
        // Required empty public constructor
        cache = TokenCache.getInstance();
        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInterface(ActivityConnectInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageView iv_profile_myPage;
    private TextView tv_profile_myPage,tv_my_reviews,tv_my_favorites;
    private Button btn_sign;
    private RecyclerView mylist;
    private ConnectFork connectFork;
    private final String LOGIN = "로그인 하기";
    private final String LOGOUT = "로그아웃";
    private ProgressBar progressBar;
    private MainViewInterface service;
    private MyFavorsAdapter mfAdapter;
    private MyReviewAdapter mrAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.mp_progress);
        iv_profile_myPage = (ImageView) view.findViewById(R.id.iv_profile_my);
        tv_profile_myPage = (TextView) view.findViewById(R.id.tv_profile_my);
        btn_sign = (Button) view.findViewById(R.id.btn_sign);
        mylist = (RecyclerView) view.findViewById(R.id.mylist);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mylist.setLayoutManager(manager);

        tv_my_reviews = (TextView)view.findViewById(R.id.tv_my_reviews);
        tv_my_favorites = (TextView)view.findViewById(R.id.tv_my_favorites);
        tv_my_favorites.setOnClickListener(this);
        tv_my_reviews.setOnClickListener(this);
        connectFork = new ConnectFork(this);
        try {
            initAdapters();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMyReviews();
        if (token.equals("")) {
            btn_sign.setText(LOGIN);
            tv_profile_myPage.setText("로그인 해주세요");
        } else {
            try {
                tv_profile_myPage.setText("ID : " + cache.readID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn_sign.setText(LOGOUT);
        }
        Glide.with(getContext())
                .load(R.mipmap.com_forkit_profile_picture_blank_portrait)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile_myPage);

        btn_sign.setOnClickListener(this);

        return view;
    }


    private void initAdapters() throws IOException {
        mfAdapter = new MyFavorsAdapter(this);
        mrAdapter = new MyReviewAdapter(this);
        mylist.setAdapter(mrAdapter);

    }

    public void setService(MainViewInterface service) {
        this.service = service;
    }

    private void setMyFavorite() {
        mfAdapter.removeallData();
        if (token.length() > 0) {
            connectFork.getMyFavorites(token);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void setMyReviews() {
        mrAdapter.removeallData();
        if (token.length() > 0) {
            connectFork.getMyReviews(token);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign:
                setSignButton(view);
                break;
            case R.id.tv_my_reviews:
                mylist.setAdapter(mrAdapter);
                setMyReviews();
                break;
            case R.id.tv_my_favorites:
                mylist.setAdapter(mfAdapter);
                setMyFavorite();
                break;
            case R.id.tv_dialog_review_cancle:
                infoDialog.cancel();
                break;
            case R.id.tv_dialog_review_ok:
                if(et_dialog_content.getText().length()>0 && et_dialog_title.getText().length()>0){
                    String cTitle = et_dialog_title.getText().toString();
                    String cContents = et_dialog_content.getText().toString();
                    String rate = dialog_ratingBar.getRating()+"";
                    connectFork.modifiReview(token,res_id,review_id,cTitle,cContents,rate);
                    infoDialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"제목과 내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }


    private void setSignButton(View view) {
        Button temp_btn = (Button) view;
        if (temp_btn.getText().toString().equals(LOGIN)) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (temp_btn.getText().toString().equals(LOGOUT)) {
            try {
                cache.delete();
                anInterface.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setData_favors(List<Results> body) {
        if (body.size() > 0) {
            mfAdapter.addDatas((ArrayList) body);
        } else {

        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData_reviews(List<Reviews> body) {
        if (body.size() > 0) {
            mrAdapter.addDatas((ArrayList) body);
        } else {

        }
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void removeFavorite(final String r_id, final String my_like_id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("삭제");
        dialog.setMessage("정말로 삭제 하시 겠습니까?");
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectFork.setRestaurantsDislike(token, r_id, my_like_id);
            }
        });
        dialog.show();

    }

    private void refresh_myfavors() {
        setMyFavorite();
    }

    public void refresh(boolean check) {
        Log.e("ag", "mfadapter = " + mfAdapter);
        Log.e("ag", "mradapter = " + mrAdapter);
        Log.e("ag", "getAdapter = " + mylist.getAdapter());
        if (mylist.getAdapter() == mfAdapter) {
            refresh_myfavors();
            Log.e("ag", "mfadapter");
        } else if (mylist.getAdapter() == mrAdapter) {
            Log.e("ag", "mradapter");
            refresh_myReviews();
        }
        if (check) {
            service.refresh_allFragment(true);
        }

    }

    @Override
    public void removeReview(final String rest_id, final String id) {
        final String rid = rest_id;
        final String rvid = id;
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("삭제");
        dialog.setMessage("정말로 삭제 하시 겠습니까?");
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectFork.removeMyReviews(token,rid,rvid);
            }
        });
        dialog.show();



    }
    private TextView tv_dialog_review_cancle,tv_dialog_review_ok;
    private AlertDialog infoDialog;
    private EditText et_dialog_title,et_dialog_content;
    private RatingBar dialog_ratingBar;
    private String res_id,review_id;
    @Override
    public void popDialog(String rest_id, String id,String title,String contents,String value) {
        res_id = rest_id;
        review_id = id;
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_review_modified, null);
        tv_dialog_review_cancle = (TextView) view.findViewById(R.id.tv_dialog_review_cancle);
        tv_dialog_review_ok = (TextView) view.findViewById(R.id.tv_dialog_review_ok);
        et_dialog_title = (EditText)view.findViewById(R.id.et_dialog_title);
        et_dialog_content = (EditText)view.findViewById(R.id.et_dialog_content);

        dialog_ratingBar = (RatingBar)view.findViewById(R.id.dialog_ratingBar);

        et_dialog_title.setText(title);
        et_dialog_content.setText(contents);
        dialog_ratingBar.setMax(5);
        dialog_ratingBar.setRating(Float.parseFloat(value));
        dialog_ratingBar.setStepSize(0.5f);
        tv_dialog_review_ok.setOnClickListener(this);
        tv_dialog_review_cancle.setOnClickListener(this);




        infoDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();

        Window window = infoDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER_VERTICAL;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        infoDialog.show();
    }

    private void refresh_myReviews() {
        setMyReviews();
    }


}

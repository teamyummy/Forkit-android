package com.yummyteam.fastcampus.forkit.main.fragment.mypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yummyteam.fastcampus.forkit.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyPage_Fragment extends Fragment {

    public MyPage_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ImageView iv_profile_myPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        iv_profile_myPage = (ImageView)view.findViewById(R.id.iv_profile_my);

        Glide.with(getContext())
                .load(R.mipmap.com_forkit_profile_picture_blank_portrait)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile_myPage);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}

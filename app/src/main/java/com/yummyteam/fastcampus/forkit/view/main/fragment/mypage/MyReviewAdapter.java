package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-12-09.
 */

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Reviews> datas;
    public MyReviewAdapter(Activity activity){
        this.activity = activity;
        datas = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reviews,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView myReview_content;
        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
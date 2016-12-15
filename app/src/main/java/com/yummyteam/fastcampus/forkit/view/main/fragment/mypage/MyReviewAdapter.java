package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-12-09.
 */

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    private ViewGroup parent;
    private ArrayList<Reviews> datas;
    private MyPageInterface mpInterface;
    public MyReviewAdapter(MyPageInterface mpInterface){
        datas = new ArrayList<>();
        this.mpInterface = mpInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reviews,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reviews review = datas.get(position);
        holder.tv_myReview_create_date.setText(review.getCreated_date().substring(0,10));
        holder.tv_myReview_title.setText(review.getTitle());
        holder.tv_myReview_restaurants_name.setText(review.getRestaurant());
        holder.tv_myReview_score.setText(review.getScore());
        holder.tv_myReview_like.setText(review.getLike());
        holder.tv_myReview_dislike.setText(review.getDislike());
        holder.tv_myReview_contents.setText(review.getContent());

        if(review.getImages().size() >0){
            MyReviewImgAdapter adapter = new MyReviewImgAdapter((ArrayList)review.getImages(),parent.getContext());
            holder.myReview_img_list.setAdapter(adapter);
        }

        holder.ib_myReview_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpInterface.removeReview(review.getRest_id(),review.getId());
            }
        });

    }

    public void addDatas(ArrayList<Reviews> sub_datas) {
        this.datas.addAll(sub_datas);
        notifyDataSetChanged();
    }

    public void removeallData() {
        datas = new ArrayList<>();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_myReview_create_date,tv_myReview_title,tv_myReview_restaurants_name,
                tv_myReview_score,tv_myReview_like,tv_myReview_dislike,tv_myReview_contents;
        private RecyclerView myReview_img_list;
        private ImageButton ib_myReview_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_myReview_create_date = (TextView)itemView.findViewById(R.id.tv_myReview_create_date);
            tv_myReview_title = (TextView)itemView.findViewById(R.id.tv_myReview_title);
            tv_myReview_restaurants_name = (TextView)itemView.findViewById(R.id.tv_myReview_restaurants_name);
            tv_myReview_score = (TextView)itemView.findViewById(R.id.tv_myReview_score);
            tv_myReview_like = (TextView)itemView.findViewById(R.id.tv_myReview_like);
            tv_myReview_dislike = (TextView)itemView.findViewById(R.id.tv_myReview_dislike);
            tv_myReview_contents = (TextView)itemView.findViewById(R.id.tv_myReview_contents);
            myReview_img_list = (RecyclerView)itemView.findViewById(R.id.myReivew_img_list);
            ib_myReview_delete = (ImageButton)itemView.findViewById(R.id.ib_myReviw_delete);
            LinearLayoutManager manager = new LinearLayoutManager(parent.getContext(),LinearLayoutManager.HORIZONTAL,false);
            myReview_img_list.setLayoutManager(manager);

        }
    }
}

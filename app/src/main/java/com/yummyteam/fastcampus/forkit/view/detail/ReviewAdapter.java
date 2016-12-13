package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-02.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    ArrayList<Reviews> reviews;
    int itemLayout;
    Context context;
    RecyclerAdapter recyclerAdapter;
    ImageView selectedImg;


    public ReviewAdapter(int itemLayout, Context context){
        reviews=new ArrayList<>();
        this.itemLayout = itemLayout;
        this.context = context;
    }
    public void addReviewData(ArrayList<Reviews> reviews){
        this.reviews=reviews;
        notifyDataSetChanged();
    }
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ViewHolder holder, int position) {
        Reviews data = reviews.get(position);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(data,R.layout.item_picture_maptodetails,context);
        holder.recyclerView.setAdapter(recyclerAdapter);


        RecyclerView.LayoutManager manager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(manager);
        holder.tvName.setText("글쓴이 : "+data.getAuthor());
        holder.tvDate.setText(data.getCreated_date());
        holder.content.setText(data.getContent());
        holder.tvLike.setText("공감 "+data.getLike()+"개 "+"  비공감 "+data.getDislike()+"개");


        Log.e("Like",data.getLike());
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLike(holder.btnLike,holder.btnDisLike);

            }
        });
        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDisLike(holder.btnDisLike,holder.btnLike);

            }
        });

    }

    public void selectLike(ImageView clickedImg,ImageView unClickedImg){

        if(selectedImg !=null){
            if(selectedImg != clickedImg){
                clickedImg.setImageResource(R.drawable.ic_egmt_review_rating_2_pressed);
                unClickedImg.setImageResource(R.mipmap.ic_egmt_review_rating_3_normal);
                selectedImg=clickedImg;


            }else{
                clickedImg.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
                selectedImg=null;
            }
        }else{
            clickedImg.setImageResource(R.drawable.ic_egmt_review_rating_2_pressed);
            selectedImg=clickedImg;

        }

    }
    public void selectDisLike(ImageView clickedImg,ImageView unClickedImg){

        if(selectedImg !=null){
            if(selectedImg != clickedImg){
                clickedImg.setImageResource(R.drawable.ic_egmt_review_rating_3_pressed);
                unClickedImg.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
                selectedImg=clickedImg;


            }else{
                clickedImg.setImageResource(R.mipmap.ic_egmt_review_rating_3_normal);
                selectedImg=null;
            }
        }else{
            clickedImg.setImageResource(R.drawable.ic_egmt_review_rating_3_pressed);
            selectedImg=clickedImg;

        }

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView tvName;
        TextView tvDate;
        TextView tvLike;

        ImageView btnLike;
        ImageView btnDisLike;
        ExpandableTextView content;


        public ViewHolder(View itemView) {
            super(itemView);

            tvName=(TextView)itemView.findViewById(R.id.tvStoreName_review);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.recyclerView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            content=(ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            tvLike=(TextView)itemView.findViewById(R.id.tvLike);
            btnLike=(ImageView)itemView.findViewById(R.id.btnLike);
            btnDisLike=(ImageView)itemView.findViewById(R.id.btnDisLike);


        }

    }
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


        int itemLayout;
        Context context;
        Reviews review;

        public RecyclerAdapter(Reviews review, int itemLayout, Context context) {

            this.review=review;
            this.itemLayout = itemLayout;
            this.context = context;
        }


        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);




            return new RecyclerAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {

            String img_src ="";
            if(review.getImages().size()==0){
                img_src = "https://yt3.ggpht.com/-Xpap6ijaRfM/AAAAAAAAAAI/AAAAAAAAAAA/eyfS-T4Pqxc/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
            }else{
                if(review.getImages().size()<3){
                    review.getImages().add(review.getImages().get(0));
                    review.getImages().add(review.getImages().get(0));
                }
                img_src = review.getImages().get(position).getImg();
            }
            Picasso.with(context).load(img_src).into(holder.reviewImg);

            Log.e("IMAGE TAG123", img_src);

        }

        @Override
        public int getItemCount() {

            if(review.getImages().size() <3){
                return 3;
            }else{

                return review.getImages().size() ;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView reviewImg;
            CardView cardView;
            TextView tvLike;

            public ViewHolder(View itemView) {
                super(itemView);
                reviewImg = (ImageView) itemView.findViewById(R.id.reviewImg);
                cardView = (CardView) itemView.findViewById(R.id.cardView);
                tvLike=(TextView)itemView.findViewById(R.id.tvLike);


            }
        }
    }

}

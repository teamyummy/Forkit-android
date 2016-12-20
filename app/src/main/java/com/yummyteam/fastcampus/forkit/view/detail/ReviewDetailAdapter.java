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
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Reviews;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-02.
 */

public class ReviewDetailAdapter extends RecyclerView.Adapter<ReviewDetailAdapter.ViewHolder> {

    private ArrayList<Reviews> reviews;
    private int itemLayout;
    private Context context;
    private RecyclerAdapter recyclerAdapter;
    private ImageView selectedImg;
    private GetResultsInterface adapterInterFace;
    private ArrayList<Boolean> checkExist;

    private Boolean existId;
    private Boolean changed;
    private int dataLike;
    private int dataDisLike;
    private ViewGroup viewGroup;
    boolean clickable;






    public ReviewDetailAdapter(int itemLayout, Context context){
        reviews=new ArrayList<>();
        this.itemLayout = itemLayout;
        this.context = context;
        checkExist=new ArrayList<>();
    }
    public void setLikeInterface(GetResultsInterface adapterInterFace){
        this.adapterInterFace=adapterInterFace;
    }

    public void addReviewData(ArrayList<Reviews> reviews){
        this.reviews=reviews;
        notifyDataSetChanged();
    }
    public void removeData(){
        reviews=new ArrayList<>();
        notifyDataSetChanged();
    }
    public void makeClickable(){
        clickable=true;
    }
    @Override
    public ReviewDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewGroup=parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewDetailAdapter.ViewHolder holder, final int position) {
        final Reviews data = reviews.get(position);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(data, R.layout.item_picture_maptodetails,context);
        holder.recyclerView.setAdapter(recyclerAdapter);


        RecyclerView.LayoutManager manager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(manager);
        holder.tvName.setText("글쓴이 : "+data.getAuthor());
        holder.tvDate.setText(data.getCreated_date());
        holder.content.setText(data.getContent());
        int intDisLike= (Integer.parseInt(data.getDislike()));
        int realDisLike=Math.abs(intDisLike);
        Log.e("REALDISLIKE",realDisLike+"");
        data.setDislike(realDisLike+"");

        holder.tvLike.setText("공감 "+data.getLike()+"개 "+"  비공감 "+data.getDislike()+"개");


        Log.e("Like",data.getLike());
        Log.e("My_like",data.getMy_like());


        clickable=true;

        final String myLike=data.getMy_like();
        Log.e("getMylIke",myLike);

        if(myLike.equals(1+"")){
            selectedImg=holder.btnLike;
            holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_pressed);
        }else if(myLike.equals(-1+"")) {
            holder.btnDisLike.setImageResource(R.drawable.ic_egmt_review_rating_3_pressed);
            selectedImg=holder.btnDisLike;
        }else{

        }

        if(myLike.equals(0+"")){
            existId=false;
        }else{
            existId=true;
        }
        checkExist.add(existId);



        dataLike= Integer.parseInt(data.getLike());
        dataDisLike= Integer.parseInt(data.getDislike());

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickable){
                    if (selectedImg != null) {
                        if (selectedImg != holder.btnLike) {
                            holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_pressed);
                            holder.btnDisLike.setImageResource(R.mipmap.ic_egmt_review_rating_3_normal);
                            selectedImg = holder.btnLike;
                            data.setMy_like(1+"");
                            ++dataLike;
                            --dataDisLike;
                            data.setLike(dataLike+"");
                            data.setDislike(dataDisLike+"");

                            holder.tvLike.setText("공감 "+data.getLike()+"개 "+"  비공감 "+data.getDislike()+"개");



                        } else {
                            holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
                            selectedImg = null;
                            data.setMy_like(0+"");
                            --dataLike;
                            data.setLike(dataLike+"");
                            data.setDislike(dataDisLike+"");

                            holder.tvLike.setText("공감 "+data.getLike()+"개 "+"  비공감 "+data.getDislike()+"개");



                        }
                    } else if (selectedImg == null) {
                        holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_pressed);
                        holder.btnDisLike.setImageResource(R.mipmap.ic_egmt_review_rating_3_normal);
                        selectedImg = holder.btnLike;
                        data.setMy_like(1+"");
                        ++dataLike;
                        data.setLike(dataLike+"");
                        data.setDislike(dataDisLike+"");

                        holder.tvLike.setText("공감 "+data.getLike()+"개 "+"  비공감 "+data.getDislike()+"개");


                    }

                    existId = checkExist.get(position);
                    if (myLike.equals(data.getMy_like())) {
                        changed = false;
                    } else {
                        changed = true;
                    }

                    Log.e("changed", changed + "");
                    Log.e("exist", checkExist.get(position) + "");
                    clickable=false;
                    adapterInterFace.setReviewLike(data.getMy_like(), data.getId(), existId, data.getMy_like_id(), changed, position);

                } else {
                    Toast.makeText(viewGroup.getContext(), "잠시만 기다려주세요", Toast.LENGTH_LONG).show();
                }


            }
        });
        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickable) {
                    if (selectedImg != null) {
                        if (selectedImg != holder.btnDisLike) {
                            holder.btnDisLike.setImageResource(R.drawable.ic_egmt_review_rating_3_pressed);
                            holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
                            selectedImg = holder.btnDisLike;
                            data.setMy_like(-1 + "");
                            --dataLike;
                            ++dataDisLike;
                            data.setLike(dataLike + "");
                            data.setDislike(dataDisLike + "");

                            holder.tvLike.setText("공감 " + data.getLike() + "개 " + "  비공감 " + data.getDislike() + "개");

                        } else {
                            holder.btnDisLike.setImageResource(R.mipmap.ic_egmt_review_rating_3_normal);
                            selectedImg = null;
                            data.setMy_like(0 + "");
                            --dataDisLike;
                            data.setLike(dataLike + "");
                            data.setDislike(dataDisLike + "");

                            holder.tvLike.setText("공감 " + data.getLike() + "개 " + "  비공감 " + data.getDislike() + "개");
                        }
                    } else {
                        holder.btnDisLike.setImageResource(R.drawable.ic_egmt_review_rating_3_pressed);
                        holder.btnLike.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
                        selectedImg = holder.btnDisLike;
                        data.setMy_like(-1 + "");
                        ++dataDisLike;
                        data.setLike(dataLike + "");
                        data.setDislike(dataDisLike + "");

                        holder.tvLike.setText("공감 " + data.getLike() + "개 " + "  비공감 " + data.getDislike() + "개");
                    }

                    existId = checkExist.get(position);
                    if (myLike.equals(data.getMy_like())) {
                        changed = false;
                    } else {
                        changed = true;
                    }

                    Log.e("changed", changed + "");
                    Log.e("exist", checkExist.get(position) + "");

                    clickable=false;
                    existId = checkExist.get(position);
                    adapterInterFace.setReviewLike(data.getMy_like(), data.getId(), existId, data.getMy_like_id(), changed, position);
                } else

                {
                    Toast.makeText(viewGroup.getContext(), "잠시만 기다려주세요", Toast.LENGTH_LONG).show();
                }

            }
        });


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


            String img_src="";
            img_src = review.getImages().get(position).getImg();
            Picasso.with(context).load(img_src).into(holder.reviewImg);



            Log.e("IMAGE TAG123", img_src);

        }

        @Override
        public int getItemCount() {

            return review.getImages().size();
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

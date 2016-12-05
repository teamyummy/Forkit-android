package com.yummyteam.fastcampus.forkit.Map;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-02.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    //ArrayList datas;
    int itemLayout;
    Context context;


    public ReviewAdapter(ArrayList datas, int itemLayout, Context context){
        //this.datas = datas;
        this.itemLayout = itemLayout;
        this.context = context;
    }
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(null,R.layout.item_picture_maptodetails,context);
        holder.recyclerView.setAdapter(recyclerAdapter);

        RecyclerView.LayoutManager manager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(manager);

        holder.tvName.setText("길라임");
        holder.tvDate.setText("2016-12-01 01:27");
        holder.content.setText("아...망할 디자인.........zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "z" +
                "z");


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView tvName;
        TextView tvDate;
        ExpandableTextView content;


        public ViewHolder(View itemView) {
            super(itemView);

            tvName=(TextView)itemView.findViewById(R.id.tvStoreName_review);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.recyclerView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            content=(ExpandableTextView) itemView.findViewById(R.id.expand_text_view);


        }
    }
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        //ArrayList datas;
        int itemLayout;
        Context context;

        public RecyclerAdapter(ArrayList datas, int itemLayout, Context context) {
            //this.datas = datas;
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


        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.img);
                cardView = (CardView) itemView.findViewById(R.id.cardView);


            }
        }
    }

}

package com.yummyteam.fastcampus.forkit.Map;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-30.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    //ArrayList datas;
    int itemLayout;
    Context context;

    public PictureAdapter(ArrayList datas, int itemLayout, Context context) {
        //this.datas = datas;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    // view 를 만들어서 홀더에 저장하는 역할
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);


        return new PictureAdapter.ViewHolder(view);
    }

    // listView getView 를 대체하는 함수
    @Override
    public void onBindViewHolder(PictureAdapter.ViewHolder holder, final int position) {

        holder.image.setImageResource(R.mipmap.food01);

    }

    @Override
    public int getItemCount() {
        return 7;
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


package com.yummyteam.fastcampus.forkit.view.detail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Images;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-30.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {


    int itemLayout;
    ArrayList<Images> images;
    Context context;

    public PictureAdapter(int itemLayout, Context context) {
        images=new ArrayList<>();
        this.itemLayout = itemLayout;
        this.context = context;
    }

    public void addImageData(ArrayList<Images> images){
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);


        return new PictureAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureAdapter.ViewHolder holder, final int position) {


        String img_src ="";
        if(images.size() == 0) {
            img_src = "https://yt3.ggpht.com/-Xpap6ijaRfM/AAAAAAAAAAI/AAAAAAAAAAA/eyfS-T4Pqxc/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
        }else{
            if(images.size()<3){
                images.add(images.get(0));
                images.add(images.get(0));
            }
            img_src = images.get(position).getImg();
        }
        Picasso.with(context).load(img_src).into(holder.image);

        Log.e("IMAGE TAG", img_src);


    }

    @Override
    public int getItemCount() {

        if(images.size() <3){
            return 3;
        }else{

            return images.size() ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.reviewImg);
            cardView = (CardView) itemView.findViewById(R.id.cardView);


        }
    }
}


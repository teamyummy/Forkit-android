package com.yummyteam.fastcampus.forkit.view.main.fragment.eatery;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.view.detail.Detail_Restaurant;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-11-29.
 */

public class ELAdapter extends RecyclerView.Adapter<ELAdapter.ViewHolder> {
    private ArrayList<Results> datas;
    private Activity activity;
    public ELAdapter(Activity activity){
        datas = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public ELAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ELAdapter.ViewHolder holder, int position) {
        final Results data = datas.get(position);

        if(data.getMy_like().equals("true")){
            holder.tv_isLike.setText(R.string.null_heart);
        }else{
            holder.tv_isLike.setText(R.string.max_heart);
        }
        holder.tv_total_review.setText(""+data.getReviews().size());
        holder.tv_avg_like.setText(data.getTotal_like()+"");
//        holder.tv_total_favorite.setText(data.getTotal_like());
        holder.tv_restaurant_name.setText(data.getName());
        holder.tv_restaurant_address.setText(data.getAddress());
        String img_src = "";
        if(data.getImages().size() == 0)
        {
            img_src = "https://yt3.ggpht.com/-Xpap6ijaRfM/AAAAAAAAAAI/AAAAAAAAAAA/eyfS-T4Pqxc/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
        }else{
            img_src = data.getImages().get(0).getImg();
        }
        Glide.with(activity)
                .load(img_src)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(holder.iv_restaurant);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Detail_Restaurant.class);
                intent.putExtra("restaurant_id",data.getId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addDatas(ArrayList<Results> sub_datas) {
        this.datas.addAll(sub_datas);
        notifyDataSetChanged();
    }

    public void removeallData(){
        this.datas.removeAll(datas);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_restaurant;
        TextView tv_total_review,tv_total_favorite,tv_avg_like,tv_isLike;
        TextView tv_restaurant_address,tv_restaurant_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_restaurant = (ImageView)itemView.findViewById(R.id.iv_restaurant);
            tv_isLike = (TextView)itemView.findViewById(R.id.tv_isLike);
            tv_avg_like = (TextView)itemView.findViewById(R.id.tv_avg_like);
            tv_total_favorite = (TextView)itemView.findViewById(R.id.tv_total_favorite);
            tv_total_review = (TextView)itemView.findViewById(R.id.tv_total_review);
            tv_restaurant_address = (TextView)itemView.findViewById(R.id.tv_restaurant_address);
            tv_restaurant_name = (TextView)itemView.findViewById(R.id.tv_restaurant_name);

        }
    }
}

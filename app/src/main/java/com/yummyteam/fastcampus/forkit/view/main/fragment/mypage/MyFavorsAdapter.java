package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.view.detail.Detail_Restaurant;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dabin on 2016-11-29.
 */

public class MyFavorsAdapter extends RecyclerView.Adapter<MyFavorsAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Results> datas;
    private TokenCache cache;
    private String token;
    private static final int LIKED = R.mipmap.ic_favorite_pink_36dp;

    private ImageButton temp_ib;
    private MyPageInterface mpInterface;

    public MyFavorsAdapter(MyPageInterface mpInterface) throws IOException {
        datas = new ArrayList<>();
        cache = TokenCache.getInstance();
        this.mpInterface = mpInterface;
        token = cache.read();
    }


    private ViewGroup parent;

    @Override
    public MyFavorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_list, parent, false);
        this.parent = parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFavorsAdapter.ViewHolder holder, int position) {
        final Results data = datas.get(position);
        holder.ib_isLike.setTag(position);
        if (data.getMy_like().equals("true")) {
            holder.ib_isLike.setBackgroundColor(Color.WHITE);
            holder.ib_isLike.setImageResource(LIKED);
        }
        holder.tv_total_review.setText("" + data.getReviews().size());
        holder.tv_avg_like.setText(data.getTotal_like() + "");
        holder.tv_total_favorite.setText(data.getTotal_like());
        holder.tv_restaurant_name.setText(data.getName());
        holder.tv_restaurant_address.setText(data.getAddress());
        String img_src = "";
        if (data.getImages().size() == 0) {
            img_src = "https://yt3.ggpht.com/-Xpap6ijaRfM/AAAAAAAAAAI/AAAAAAAAAAA/eyfS-T4Pqxc/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
        } else {
            img_src = data.getImages().get(0).getImg();
        }
        Glide.with(parent.getContext())
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
                Intent intent = new Intent(parent.getContext(), Detail_Restaurant.class);
                intent.putExtra("restaurant_id", data.getId());
                parent.getContext().startActivity(intent);
            }
        });

        holder.ib_isLike.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addDatas(ArrayList<Results> sub_datas) {
        this.datas.addAll(sub_datas);
        notifyDataSetChanged();
    }

    public void removeallData() {
        this.datas.removeAll(datas);
        notifyDataSetChanged();
    }

    private boolean clickable = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_isLike:
                Log.e("button", "click able = " + clickable);
                if (token.length() > 0 && clickable) {
                    ImageButton ib = (ImageButton) view;
                    if (ib.getTag() != null) {
                        int position = (Integer) ib.getTag();
                        String like = datas.get(position).getMy_like();
                        if (like.equals("true")) {
                            mpInterface.removeFavorite(datas.get(position).getId(),datas.get(position).getMy_like_id());
                        }
                    } else {
                        Log.e("tag", "ib tag is null!");
                    }
                }
                break;
        }


    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_restaurant;
        TextView tv_total_review, tv_total_favorite, tv_avg_like;
        TextView tv_restaurant_address, tv_restaurant_name;
        ImageButton ib_isLike;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_restaurant = (ImageView) itemView.findViewById(R.id.iv_restaurant);
            ib_isLike = (ImageButton) itemView.findViewById(R.id.ib_isLike);
            tv_avg_like = (TextView) itemView.findViewById(R.id.tv_avg_like);
            tv_total_favorite = (TextView) itemView.findViewById(R.id.tv_total_favorite);
            tv_total_review = (TextView) itemView.findViewById(R.id.tv_total_review);
            tv_restaurant_address = (TextView) itemView.findViewById(R.id.tv_restaurant_address);
            tv_restaurant_name = (TextView) itemView.findViewById(R.id.tv_restaurant_name);

        }
    }
}

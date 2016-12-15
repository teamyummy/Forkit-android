package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Images;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-12-14.
 */

public class MyReviewImgAdapter  extends RecyclerView.Adapter<MyReviewImgAdapter.ViewHolder> {
    private ArrayList<Images> datas;
    private ViewGroup parent;
    private Context context;
    public MyReviewImgAdapter(ArrayList<Images> datas, Context context){
        this.datas =datas;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_reviews_img,parent,false);
        Log.e("veiw","view = " + view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Images image = datas.get(position);

        Glide.with(context)
                .load(image.getImg_t())
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
                .into(holder.myReviews_img);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView myReviews_img;
        public ViewHolder(View itemView) {
            super(itemView);
            myReviews_img = (ImageView)itemView.findViewById(R.id.myReview_img);
        }
    }
}

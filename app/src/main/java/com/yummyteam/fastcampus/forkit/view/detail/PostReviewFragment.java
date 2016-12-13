package com.yummyteam.fastcampus.forkit.view.detail;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork2;
import com.yummyteam.fastcampus.forkit.view.map.GetResultsInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostReviewFragment extends DialogFragment implements GetResultsInterface {

    ImageView igAddPhoto;
    ViewPager viewPager;
    ArrayList<Image> images;
    ArrayList<Bitmap> fileBtimaps;
    RatingBar ratingBar;
    TextView rb_tv;
    EditText etReview;

    Button btnOk;
    Button btnCancel;

    String score;
    private String token;
    String content;

    private TokenCache cache;




    public PostReviewFragment() {
        cache = TokenCache.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final ConnectFork2 connectFork2=new ConnectFork2(this);

        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }




        View view = inflater.inflate(R.layout.fragment_postreview, container, false);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        rb_tv = (TextView) view.findViewById(R.id.rb_tv);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb_tv.setText(rating+"/5");
                score=((int)rating)+"";
            }
        });
        etReview=(EditText)view.findViewById(R.id.etReview);


        btnOk=(Button)view.findViewById(R.id.btnOk);
        btnCancel=(Button)view.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=etReview.getText().toString();

                connectFork2.postReview(token,content,score);
                Log.e("Token", token);
                Log.e("Content", content);
                Log.e("score", score+"");

            }
        });

        igAddPhoto=(ImageView)view.findViewById(R.id.igAddPhoto);
        igAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });


        viewPager=(ViewPager)view.findViewById(R.id.viewPager);



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            fileBtimaps=new ArrayList<>();
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            for(int i= 0, l=images.size();i<l;i++){
                fileBtimaps.add(getThumbnailImage(images.get(i).path));
            }
//            for (int i = 0, l = images.size(); i < l; i++) {
//                fileUris.add(new File(images.get(i).path).toURI());
//            }
            igAddPhoto.setVisibility(View.INVISIBLE);

            CustomAdapter adapter= new CustomAdapter(getActivity().getLayoutInflater());
            viewPager.setAdapter(adapter);


        }

    }

    private Bitmap getThumbnailImage(String imgPath) {
        Bitmap image = null;
        try {
            // 이미지 축소
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3; // 1/3로 축소

            image = BitmapFactory.decodeFile(imgPath,options);

            Log.i("image","instance="+image);
            int exifDegree = exifOrientationToDegrees(imgPath);
            Log.i("image","exifDegree="+exifDegree);
            image = rotateImage(image, exifDegree);

        }catch (Exception e){
            Log.e("Thumbnail Error",e.toString());
            e.printStackTrace();
        }
        return image;
    }
    public int exifOrientationToDegrees(String imgPath){
        int degree = 0;
        ExifInterface exif = null;

        try{
            exif = new ExifInterface(imgPath);
        }catch (IOException e){
            Log.e("exifOrientation", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null){
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1){
                // We only recognize a subset of orientation tag values.
                switch(orientation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    @Override
    public void getList(List<Results> data) {

    }

    @Override
    public void getDetail(Results data) {

    }

    public class CustomAdapter extends PagerAdapter {

        LayoutInflater inflater;

        public CustomAdapter(LayoutInflater inflater) {
            // TODO Auto-generated constructor stub


            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            View view = null;
            view = inflater.inflate(R.layout.item_picture_maptodetails, null);

            ImageView imageView=(ImageView)view.findViewById(R.id.imageView4);
            imageView.setImageBitmap(fileBtimaps.get(position));


            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub

            container.removeView((View) object);

        }

        @Override
        public boolean isViewFromObject(View v, Object obj) {
            // TODO Auto-generated method stub
            return v == obj;
        }

    }

}

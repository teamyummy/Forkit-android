package com.yummyteam.fastcampus.forkit.view.detail;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageView igAddPhoto;
    private ViewPager viewPager;

    private ArrayList<Bitmap> fileBtimaps;
    ArrayList<String> filePath;
    private ArrayList<Image> images;
    private RatingBar ratingBar;
    private TextView rb_tv;
    private EditText etReview;
    private EditText etTitle;

    private Button btnOk;
    private Button btnCancel;

    private String score;
    private String token;
    private String content;
    private String title;

    private String id;
    private TokenCache cache;

    private final static int REQ_CODE_CAMERA = 10;
    private final static int REQ_CODE_GALLERY = 20;

    private AlertDialog infoDialog;

    PostListner mListener;





    public PostReviewFragment(String id) {
        cache = TokenCache.getInstance();
        images=new ArrayList<>();
        this.id=id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

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

        score=0+"";


        View view = inflater.inflate(R.layout.fragment_postreview, container, false);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        rb_tv = (TextView) view.findViewById(R.id.rb_tv);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb_tv.setText(rating+"/5");
                score=rating+"";
            }
        });

        etReview=(EditText)view.findViewById(R.id.etReview);
        etTitle=(EditText)view.findViewById(R.id.etTitle);


        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=etReview.getText().toString();
                title=etReview.getText().toString();



                if (content.getBytes().length <= 0 || title.getBytes().length <= 0) {

                    Toast.makeText(getContext(), "제목,내용을 모두 입력해주세요", Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("등록");
                    dialog.setMessage("리뷰를 등록 하시겠습니까?");
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            connectFork2.postReview(token,id, title, content, score, images);
                            Log.e("Token", token);
                            Log.e("Content", content);
                            Log.e("score", score + "");
                            mListener.refresh();
                            dismiss();
                        }
                    });
                    dialog.show();

                }

            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=etReview.getText().toString();
                title=etReview.getText().toString();

                if(content.getBytes().length>0||title.getBytes().length>0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("종료");

                    dialog.setMessage("작성중인 게시물이 있습니다. 창을 종료 하시겠습니까?");
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dismiss();
                        }
                    });
                    dialog.show();

                }else{
                    dismiss();
                }


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

//                Intent intent = new Intent(Intent.ACTION_PICK
//                        , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*"); // 이미지만 필터링
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"),REQ_CODE_GALLERY);


            }
        });

        viewPager=(ViewPager)view.findViewById(R.id.viewPager);



        return view;
    }

    public interface PostListner{

        void refresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (PostListner)getActivity();
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            //The array list has the image paths of the selected images
            if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

                fileBtimaps = new ArrayList<>();

                images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                for (int i = 0, l = images.size(); i < l; i++) {
                    fileBtimaps.add(getThumbnailImage(images.get(i).path));

                }

            }
            igAddPhoto.setVisibility(View.INVISIBLE);
            CustomAdapter adapter = new CustomAdapter(getActivity().getLayoutInflater());
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

    @Override
    public void getLikeList(String favors) {

    }

    @Override
    public void setReviewLike(String myLike, String reviewId, Boolean existId, String lkId, Boolean changed) {

    }

    @Override
    public void getMyLikeReview(Results data) {

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

            ImageView imageView=(ImageView)view.findViewById(R.id.reviewImg);
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

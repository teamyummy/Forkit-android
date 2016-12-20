package com.yummyteam.fastcampus.forkit.view.map;

import com.yummyteam.fastcampus.forkit.model.Results;

import java.util.List;

/**
 * Created by user on 2016-12-07.
 */

public interface GetResultsInterface {

        void getList(List<Results> data);
        void getDetail(Results data);
        void getLikeList(String f_id);
        void setReviewLike(String myLike,String reviewId,Boolean existId,String lkId,Boolean changed,int position);
        void getMyLikeReview(Results data);
        void refresh(String lkId,int position);


}

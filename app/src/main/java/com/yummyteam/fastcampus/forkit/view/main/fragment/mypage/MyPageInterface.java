package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import com.yummyteam.fastcampus.forkit.model.Results;
import com.yummyteam.fastcampus.forkit.model.Reviews;

import java.util.List;

/**
 * Created by Dabin on 2016-12-14.
 */

public interface MyPageInterface {
    void setData_favors(List<Results> body);
    void setData_reviews(List<Reviews> body);
    void removeFavorite(String id, String my_like_id);

    void refresh(boolean check);

    void removeReview(String rest_id, String id);
}

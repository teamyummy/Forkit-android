package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import com.yummyteam.fastcampus.forkit.model.Results;

import java.util.List;

/**
 * Created by Dabin on 2016-12-14.
 */

public interface MyPageInterface {
    void setData(List<Results> body);

    void removeFavorite(String id, String my_like_id);

    void refresh(boolean check);
}

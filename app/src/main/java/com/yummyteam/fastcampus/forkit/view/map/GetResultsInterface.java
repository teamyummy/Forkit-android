package com.yummyteam.fastcampus.forkit.view.map;

import com.yummyteam.fastcampus.forkit.model.Results;

import java.util.List;

/**
 * Created by user on 2016-12-07.
 */

public interface GetResultsInterface {

        void getList(List<Results> data);
        void getDetail(Results data);

}

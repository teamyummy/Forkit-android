package com.yummyteam.fastcampus.forkit.view.search;

import com.yummyteam.fastcampus.forkit.model.Results;

import java.util.List;

/**
 * Created by Dabin on 2016-12-08.
 */

public interface SearchInterface {
    public void getKeyWord(String Keyword);

    void setResult(List<Results> results);

    void showAlert();
}

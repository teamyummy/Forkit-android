package com.yummyteam.fastcampus.forkit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016-12-10.
 */

public class PostReviewResponse {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("score")
    @Expose

    private int score;
    @SerializedName("like")
    @Expose
    private int like;
    @SerializedName("dislike")
    @Expose
    private int dislike;


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }


}

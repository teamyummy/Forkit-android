package com.yummyteam.fastcampus.forkit.model;

import java.util.List;

/**
 * Created by Dabin on 2016-12-06.
 */

public class Reviews {
    private String created_date;

    private String content;

    private String id;

    private String author;

    private String title;

    private String score;

    private List<Images> images;

    private String dislike;

    private String like;

    private String restaurant;

    private String rest_id;

    private String my_like;

    private String my_like_id;


    public String getMy_like() {
        return my_like;
    }

    public void setMy_like(String my_like) {
        this.my_like = my_like;
    }

    public String getMy_like_id() {
        return my_like_id;
    }

    public void setMy_like_id(String my_like_id) {
        this.my_like_id = my_like_id;
    }

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    public List<Images> getImages ()
    {
        return images;
    }

    public void setImages (List<Images> images)
    {
        this.images = images;
    }

    public String getDislike ()
    {
        return dislike;
    }

    public void setDislike (String dislike)
    {
        this.dislike = dislike;
    }

    public String getLike ()
    {
        return like;
    }

    public void setLike (String like)
    {
        this.like = like;
    }

    public String getRestaurant ()
    {
        return restaurant;
    }

    public void setRestaurant (String restaurant)
    {
        this.restaurant = restaurant;
    }


    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", content = "+content+", id = "+id+", author = "+author+", title = "+title+", score = "+score+", images = "+images+", dislike = "+dislike+", like = "+like+", restaurant = "+restaurant+"]";
    }
}

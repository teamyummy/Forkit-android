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

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", content = "+content+", id = "+id+", author = "+author+", title = "+title+", score = "+score+", images = "+images+", dislike = "+dislike+", like = "+like+", restaurant = "+restaurant+"]";
    }
}

package com.yummyteam.fastcampus.forkit.model;

/**
 * Created by Dabin on 2016-12-06.
 */

public class Images {
    private String id;

    private String img_s;

    private String alt;

    private String img;

    private String img_t;

    private String review;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getImg_s ()
    {
        return img_s;
    }

    public void setImg_s (String img_s)
    {
        this.img_s = img_s;
    }

    public String getAlt ()
    {
        return alt;
    }

    public void setAlt (String alt)
    {
        this.alt = alt;
    }

    public String getImg ()
    {
        return img;
    }

    public void setImg (String img)
    {
        this.img = img;
    }

    public String getImg_t ()
    {
        return img_t;
    }

    public void setImg_t (String img_t)
    {
        this.img_t = img_t;
    }

    public String getReview ()
    {
        return review;
    }

    public void setReview (String review)
    {
        this.review = review;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", img_s = "+img_s+", alt = "+alt+", img = "+img+", img_t = "+img_t+", review = "+review+"]";
    }
}

package com.yummyteam.fastcampus.forkit.model;

/**
 * Created by Dabin on 2016-11-29.
 */
public class Menus {
    private String id;

    private String img_s;

    private String price;

    private String description;

    private String name;

    private String img;

    private String restaurant;

    private String img_t;

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

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImg ()
    {
        return img;
    }

    public void setImg (String img)
    {
        this.img = img;
    }

    public String getRestaurant ()
    {
        return restaurant;
    }

    public void setRestaurant (String restaurant)
    {
        this.restaurant = restaurant;
    }

    public String getImg_t ()
    {
        return img_t;
    }

    public void setImg_t (String img_t)
    {
        this.img_t = img_t;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", img_s = "+img_s+", price = "+price+", description = "+description+", name = "+name+", img = "+img+", restaurant = "+restaurant+", img_t = "+img_t+"]";
    }
}

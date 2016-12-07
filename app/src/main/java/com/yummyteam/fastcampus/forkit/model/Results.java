package com.yummyteam.fastcampus.forkit.model;

import java.util.List;

/**
 * Created by Dabin on 2016-11-29.
 */

public class Results {
    private List<Tags> tags;

    private String created_date;

    private String desc_delivery;

    private List<Menus> menus;

    private List<Reviews> reviews;

    private String phone;

    private String operation_hour;

    private String review_count;

    private String my_like;

    private String id;

    private String register;

    private String address;

    private String description;

    private String total_like;

    private String name;

    private String review_score;

    private List<Images> images;

    private String longitude;

    private String latitude;

    private String can_parking;

    private String desc_parking;

    public List<Tags> getTags ()
    {
        return tags;
    }

    public void setTags (List<Tags> tags)
    {
        this.tags = tags;
    }

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public String getDesc_delivery ()
    {
        return desc_delivery;
    }

    public void setDesc_delivery (String desc_delivery)
    {
        this.desc_delivery = desc_delivery;
    }

    public List<Menus> getMenus ()
    {
        return menus;
    }

    public void setMenus (List<Menus> menus)
    {
        this.menus = menus;
    }

    public List<Reviews> getReviews ()
    {
        return reviews;
    }

    public void setReviews (List<Reviews> reviews)
    {
        this.reviews = reviews;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getOperation_hour ()
    {
        return operation_hour;
    }

    public void setOperation_hour (String operation_hour)
    {
        this.operation_hour = operation_hour;
    }

    public String getReview_count ()
    {
        return review_count;
    }

    public void setReview_count (String review_count)
    {
        this.review_count = review_count;
    }

    public String getMy_like ()
    {
        return my_like;
    }

    public void setMy_like (String my_like)
    {
        this.my_like = my_like;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRegister ()
    {
        return register;
    }

    public void setRegister (String register)
    {
        this.register = register;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTotal_like ()
    {
        return total_like;
    }

    public void setTotal_like (String total_like)
    {
        this.total_like = total_like;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getReview_score ()
    {
        return review_score;
    }

    public void setReview_score (String review_score)
    {
        this.review_score = review_score;
    }

    public List<Images> getImages ()
    {
        return images;
    }

    public void setImages (List<Images> images)
    {
        this.images = images;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getCan_parking ()
    {
        return can_parking;
    }

    public void setCan_parking (String can_parking)
    {
        this.can_parking = can_parking;
    }

    public String getDesc_parking ()
    {
        return desc_parking;
    }

    public void setDesc_parking (String desc_parking)
    {
        this.desc_parking = desc_parking;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tags = "+tags+", created_date = "+created_date+", desc_delivery = "+desc_delivery+", menus = "+menus+", reviews = "+reviews+", phone = "+phone+", operation_hour = "+operation_hour+", review_count = "+review_count+", my_like = "+my_like+", id = "+id+", register = "+register+", address = "+address+", description = "+description+", total_like = "+total_like+", name = "+name+", review_score = "+review_score+", images = "+images+", longitude = "+longitude+", latitude = "+latitude+", can_parking = "+can_parking+", desc_parking = "+desc_parking+"]";
    }
}


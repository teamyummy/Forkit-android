package com.yummyteam.fastcampus.forkit.model;

/**
 * Created by user on 2016-12-15.
 */

public class ReviewLike {

        private String created_date;

        private String id;

        private String up_and_down;

        private String user;

        private String review;

        public String getCreated_date ()
        {
            return created_date;
        }

        public void setCreated_date (String created_date)
        {
            this.created_date = created_date;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getUp_and_down ()
        {
            return up_and_down;
        }

        public void setUp_and_down (String up_and_down)
        {
            this.up_and_down = up_and_down;
        }

        public String getUser ()
        {
            return user;
        }

        public void setUser (String user)
        {
            this.user = user;
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
            return "ClassPojo [created_date = "+created_date+", id = "+id+", up_and_down = "+up_and_down+", user = "+user+", review = "+review+"]";
        }

}

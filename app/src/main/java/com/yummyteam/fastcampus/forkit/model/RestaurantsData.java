package com.yummyteam.fastcampus.forkit.model;

import java.util.List;

/**
 * Created by Dabin on 2016-12-06.
 */

public class RestaurantsData {
    private String count;
    private String next;
    private String previous;
    private List<Results> results;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}

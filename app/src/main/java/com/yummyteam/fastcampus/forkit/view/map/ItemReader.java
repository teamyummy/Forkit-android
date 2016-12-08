package com.yummyteam.fastcampus.forkit.view.map;

import com.yummyteam.fastcampus.forkit.model.Results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016-11-11.
 */

public class ItemReader {

    ArrayList<Results> datas;

    public ItemReader(ArrayList<Results> datas){
        this.datas=datas;
    }

    public List<Item> read()  {
        List<Item> items = new ArrayList<Item>();


        for (int i = 0; i < datas.size(); i++) {
            double lat = Double.parseDouble(datas.get(i).getLatitude());
            double lng = Double.parseDouble(datas.get(i).getLongitude());
            int flag=i;

            items.add(new Item(lat, lng,flag));
        }
        return items;
    }
}

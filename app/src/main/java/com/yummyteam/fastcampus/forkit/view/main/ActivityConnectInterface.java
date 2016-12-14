package com.yummyteam.fastcampus.forkit.view.main;

/**
 * Created by Dabin on 2016-12-12.
 */

public interface ActivityConnectInterface {
    public void setFavorite(String r_id,String like,String f_id);

    public void getFavorite(String r_id,String f_id,String like);
    public void refresh();
}

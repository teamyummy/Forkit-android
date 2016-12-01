package com.yummyteam.fastcampus.forkit.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yummyteam.fastcampus.forkit.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;


/**
 * A simple {@link Fragment} subclass.
 */
public class EateryListFragment extends Fragment {


    public EateryListFragment() {
        // Required empty public constructor
    }

    private RecyclerView restaurant_list;


    ArrayList<Restaurant_Info> datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_eatery_list, container, false);
        restaurant_list = (RecyclerView)view.findViewById(R.id.restaurant_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        restaurant_list.setLayoutManager(manager);
        initData();
        ELAdapter elAdapter = new ELAdapter(getActivity());
        elAdapter.addDatas(datas);
        restaurant_list.setAdapter(elAdapter);
        return view;
    }

    private void initData() {
        datas = new ArrayList<>();
        Restaurant_Info data1 = new Restaurant_Info();
        data1.setName("오리농원");
        data1.setAddress("신사역에서 좀만 더가봐요");
        data1.setScore(4);
        data1.setTotal_review(2000);
        data1.setTotal_like(100);
        data1.setLike(false);
        datas.add(data1);

        Restaurant_Info data2 = new Restaurant_Info();
        data2.setName("서브웨이");
        data2.setAddress("망고식스옆에?");
        data2.setScore(5);
        data2.setTotal_review(2600);
        data2.setTotal_like(102);
        data2.setLike(true);
        datas.add(data2);

    }


}

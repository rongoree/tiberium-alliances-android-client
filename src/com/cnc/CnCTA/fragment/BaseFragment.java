package com.cnc.CnCTA.fragment;

import android.support.v4.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cnc.CnCTA.R;
import com.cnc.CnCTA.adapter.BuildingAdapter;

import java.util.ArrayList;

public class BaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.base_buildings);
        Point cellSize = updateGridMargins(gridview);

        ArrayList<String> ar = new ArrayList<String>();
        for (int i = 0; i < 8 * 9; i++) {
            ar.add("");
        }
        BuildingAdapter aa = new BuildingAdapter(getActivity(), R.layout.base_building_cell, cellSize, ar);
        gridview.setAdapter(aa);
        return view;
    }

    private Point updateGridMargins(GridView gridview) {
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        float scaleX = size.x / 1024.0f;
        float scaleY = size.y / 768.0f;
        int bottom = (int) (38 * scaleY);
        int top = (int) (75 * scaleY);
        int left = (int) (90 * scaleX);
        int right = (int) (90 * scaleX);
        gridview.setPadding(left, top, right, bottom);
        return new Point((size.x - left - right) / 9, (size.y - top - bottom ) / 8);
    }
}

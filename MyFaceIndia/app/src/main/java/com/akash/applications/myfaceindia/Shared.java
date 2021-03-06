package com.akash.applications.myfaceindia;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import PostAdapter.CustomAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Shared extends Fragment {


    ListView lv;
    Context context;
    ArrayList myList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context=getContext();

        lv=(ListView) getView().findViewById(R.id.listViewAllEvents);

        lv.setAdapter(new CustomAdapter(getActivity().getBaseContext()));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false);
    }

}

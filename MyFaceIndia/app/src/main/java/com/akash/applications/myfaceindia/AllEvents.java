package com.akash.applications.myfaceindia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllEvents.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllEvents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllEvents extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_events, container, false);
    }


}

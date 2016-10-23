package com.akash.applications.myfaceindia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline extends Fragment {


    ImageView accessControllImageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accessControllImageView = (ImageView) getView().findViewById(R.id.ivTimelineAccessControll);
        registerForContextMenu(accessControllImageView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.access_control_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.publicAccess:
                accessControllImageView.setImageResource(R.drawable.publicbtn);
                return true;
            case R.id.friendAccess:
                accessControllImageView.setImageResource(R.drawable.friends);
                return true;
            case R.id.privateAccess:
                accessControllImageView.setImageResource(R.drawable.privatebtn);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public Timeline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

}

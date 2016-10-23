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
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment {

    ImageView accessControllImageView,arrowiv;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accessControllImageView = (ImageView) getView().findViewById(R.id.ivMainPageAccessControll);
        arrowiv = (ImageView)getView().findViewById(R.id.hideshowpost);
        final LinearLayout layout = (LinearLayout)getView().findViewById(R.id.postContentLayout);
        arrowiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility()==View.VISIBLE)
                {
                    layout.setVisibility(View.GONE);
                    arrowiv.setImageResource(android.R.drawable.arrow_up_float);
                }
                else
                {
                    layout.setVisibility(View.VISIBLE);
                    arrowiv.setImageResource(android.R.drawable.arrow_down_float);
                }
            }
        });
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

    public MainPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

}

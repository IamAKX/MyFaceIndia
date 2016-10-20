package com.akash.applications.myfaceindia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Akash on 19-10-2016.
 */
public class AdapterForText extends ArrayAdapter<PostItemText> {

    private final Context context;
    private final ArrayList<PostItemText> itemTextArrayList;

    public AdapterForText(Context context,ArrayList<PostItemText> itemTextArrayList)
    {
        super(context,R.layout.post_text_item,itemTextArrayList);
        this.context = context;
        this.itemTextArrayList = itemTextArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.post_text_item, parent, false);
        TextView status = (TextView) rowView.findViewById(R.id.postItemTextView);
        TextView date = (TextView) rowView.findViewById(R.id.postItemTextDateTime);
        status.setText(itemTextArrayList.get(position).getStatus());
        //valueView.setText(itemsArrayList.get(position).getDescription());
        date.setText(filterDateTime(itemTextArrayList.get(position).getDatetime()));
        return rowView;
    }

    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return  formattedDate;
    }
    private String filterDateTime(String dateStart) {
        String s="";
        String dateStop = getDateTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        diff/=1000;
        long diffYear = diff / (12*30*24*60*60);
       // diff %=(12*30*24*60*60);

        long diffMonth = diff /(30*24*60*60);
        //diff%=(30*24*60*60);
        long diffDay = diff / (24*60*60);
        //diff%=(24*60*60);
        long diffHours = diff / (60 * 60);
        //diff%=(60*60);
        long diffMinutes = diff / 60;
        long diffSeconds = diff  % 60;

        if(diffYear>0)
        {
            s+=diffYear+" ago";
            return s;
        }else if(diffMonth>0)
        {
            s+=diffMonth+" ago";
            return s;
        }
        else
        if(diffDay>0)
        {
            s+=diffDay+" ago";
            return s;
        }
        else
        if(diffHours>0)
        {
            s+=diffHours+" ago";
            return s;
        }
        else if(diffMinutes>0)
        {
            s+=diffMinutes+" ago";
            return s;
        }
        else
            return diffSeconds+" ago";

    }
}

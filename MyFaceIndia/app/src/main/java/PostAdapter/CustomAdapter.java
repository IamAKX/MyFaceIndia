package PostAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.R;

import java.util.ArrayList;

/**
 * Created by Akash on 23-10-2016.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayList<PostModel> list;
    Context context;
    public CustomAdapter(Context c) {
        list = new ArrayList<PostModel>();
        String[] uname = {"Justin","Peter","Mark","Lisa","Aliana"};
        String[] timePost= {"16 min ago","1 hour ago","4 hour ago","2 days ago","3 months ago"};
        String[] post = {"Post 1","Post 2","Post 3","Post 4","Post 5"};
        for(int i=0;i<5;i++)
        {
            list.add(new PostModel(uname[i],timePost[i],post[i]));
        }
        context=c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Context"," "+context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.post_card_layout,null,false);

        TextView tvUname = (TextView)row.findViewById(R.id.postUserName);
        TextView tvTime = (TextView)row.findViewById(R.id.postUserTime);
        TextView tvPost = (TextView)row.findViewById(R.id.postCardPost);

        PostModel tem = list.get(position);
        tvUname.setText(tem.userName);
        tvTime.setText(tem.time);
        tvPost.setText(tem.post);
        return row;
    }
}

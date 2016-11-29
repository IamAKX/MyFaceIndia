package Events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akash.applications.myfaceindia.EventPictureActivity;
import com.akash.applications.myfaceindia.EventVideoActivity;
import com.akash.applications.myfaceindia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import HelperPackage.DateDifference;
import HelperPackage.UserConfig;

/**
 * Created by Akash on 09-11-2016.
 */

public class AdapterForEvents extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<EventsModel> data;

    public AdapterForEvents(Context context, ArrayList<EventsModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new EventFetchViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EventFetchViewHolder _holder = (EventFetchViewHolder) holder;

        Glide.with(context).load(UserConfig.PROFILE_PIC_URL+data.get(position).uimage)
                .placeholder(R.drawable.group)
                .into(_holder.uimage);
        _holder.uname.setText(data.get(position).uname);
        _holder.name.setText(data.get(position).name);
        _holder.url.setText(data.get(position).url);

        Date postDate = DateDifference.stringToDate(data.get(position).edatetime);
        Date nowDate = DateDifference.stringToDate(DateDifference.timeNow());

        _holder.time.setText(DateDifference.dateDifference(postDate, nowDate));

        if(!data.get(position).type.equalsIgnoreCase("4"))
            _holder.layout.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class EventFetchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        ImageView uimage;
        TextView uname,name,url,time;
        public EventFetchViewHolder(View item) {
            super(item);
            uimage = (ImageView)item.findViewById(R.id.event_userdp);
            layout = (LinearLayout)item.findViewById(R.id.event_url_layout);
            uname = (TextView)item.findViewById(R.id.event_uname);
            name = (TextView)item.findViewById(R.id.event_name);
            url = (TextView)item.findViewById(R.id.event_url);
            time = (TextView)item.findViewById(R.id.event_date);

            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.get(getAdapterPosition()).type.equals("2")) {
                        Intent intent = new Intent(v.getContext(), EventPictureActivity.class);
                        intent.putExtra("imgurl", data.get(getAdapterPosition()).url);
                        v.getContext().startActivity(intent);
                    }
                    else
                    if(data.get(getAdapterPosition()).type.equals("3")) {
                        Intent intent = new Intent(v.getContext(), EventVideoActivity.class);
                        intent.putExtra("imgurl", data.get(getAdapterPosition()).url);
                        v.getContext().startActivity(intent);
                    }
                    else
                        if(data.get(getAdapterPosition()).type.equals("1"))
                        {
                            String musicURL = data.get(getAdapterPosition()).url;
                            if (!musicURL.startsWith("http://") && !musicURL.startsWith("https://"))
                                musicURL = "http://" + musicURL;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(musicURL));
                            v.getContext().startActivity(browserIntent);
                        }
                }
            });
        }
    }
}

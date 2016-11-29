package PostAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akash.applications.myfaceindia.R;

import java.util.ArrayList;
import java.util.Date;

import HelperPackage.DateDifference;

/**
 * Created by Akash on 28-10-2016.
 */

public class AdapterForTimeline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<TimelineModel> list = new ArrayList<>();
    Context context;

    public AdapterForTimeline(Context context, ArrayList<TimelineModel> data) {
        this.list = data;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.timeline_row, parent, false);
        return new TimelineViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimelineViewHolder _holder = (TimelineViewHolder) holder;
        TimelineModel tem = list.get(position);
        Date startDate = DateDifference.stringToDate(tem.date);
        Date stopDate = DateDifference.stringToDate(DateDifference.timeNow());

        _holder.dateTextView.setText(DateDifference.dateDifference(startDate, stopDate));
        _holder.statusTextView.setText(tem.status);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private class TimelineViewHolder extends RecyclerView.ViewHolder {
        TextView statusTextView, dateTextView;

        TimelineViewHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.timeline_row_datetime);
            statusTextView = (TextView) itemView.findViewById(R.id.timeline_row_status);
        }
    }
}

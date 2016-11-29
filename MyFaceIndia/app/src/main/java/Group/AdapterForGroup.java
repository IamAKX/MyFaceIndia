package Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akash.applications.myfaceindia.GroupPage;
import com.akash.applications.myfaceindia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import HelperPackage.UserConfig;

/**
 * Created by Akash on 08-11-2016.
 */

public class AdapterForGroup extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<GroupModel> data;

    public AdapterForGroup(Context context, ArrayList<GroupModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.mygroup_list_item, parent, false);
        return new GroupFetchViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GroupFetchViewHolder _holder = (GroupFetchViewHolder) holder;
        _holder.groupName.setText(data.get(position).name);
        Glide.with(context).load(UserConfig.GROUP_PIC_URL+data.get(position).image)
                .placeholder(R.drawable.group)
                .into(_holder.groupIcon);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class GroupFetchViewHolder extends RecyclerView.ViewHolder {

        ImageView groupIcon;
        TextView groupName;
        LinearLayout groupLayout;

        public GroupFetchViewHolder(View item) {
            super(item);
            groupIcon = (ImageView)item.findViewById(R.id.GroupItem_GroupIcon);
            groupName = (TextView)item.findViewById(R.id.GroupItem_GroupName);
            groupLayout = (LinearLayout)item.findViewById(R.id.mygrouplistItem_layout);
            groupLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), GroupPage.class);
                    intent.putExtra("gid", data.get(getAdapterPosition()).id);
                    intent.putExtra("image", UserConfig.GROUP_PIC_URL +
                            data.get(getAdapterPosition()).image);
                    intent.putExtra("name",data.get(getAdapterPosition()).name);
                    intent.putExtra("title",data.get(getAdapterPosition()).title);
                    intent.putExtra("date",data.get(getAdapterPosition()).gdate);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

package Search;

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

import com.akash.applications.myfaceindia.R;
import com.akash.applications.myfaceindia.UserTimeLine;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import HelperPackage.UserConfig;

/**
 * Created by Amandeep on 11/11/2016.
 */


public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SearchResModel> data;

    public SearchAdapter(Context context, ArrayList<SearchResModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_res, parent, false);
        return new SearchItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchItem _holder = (SearchItem) holder;
        _holder.userName.setText(data.get(position).getuName());
        Uri imageUri = Uri.parse(UserConfig.PROFILE_PIC_URL + data.get(position).getuImage());
        Glide.with(context).load(imageUri)
                .placeholder(R.drawable.userdp)
                .into(_holder.userImage);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private class SearchItem extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView userImage;
        LinearLayout ll;


        public SearchItem(View itemView) {
            super(itemView);

            userImage = (ImageView) itemView.findViewById(R.id.search_res_image);
            userName = (TextView) itemView.findViewById(R.id.search_res_usr);
            ll = (LinearLayout) itemView.findViewById(R.id.search_res_view);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UserTimeLine.class);
                    SearchResModel model = data.get(getAdapterPosition());
                    i.putExtra("uID", model.getuId());
                    i.putExtra("uName", model.getuName());
                    i.putExtra("uImage", UserConfig.PROFILE_PIC_URL + model.getuImage());
                    context.startActivity(i);
                }
            });
        }
    }
}

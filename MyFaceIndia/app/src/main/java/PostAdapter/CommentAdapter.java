package PostAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akash.applications.myfaceindia.R;

import java.util.ArrayList;

import HelperPackage.DateDifference;

/**
 * Created by Amandeep on 11/4/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CommentModel> data = new ArrayList<>();
    private Context context;

    public CommentAdapter(ArrayList<CommentModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        return new CommentItemHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentModel comment = data.get(position);
        CommentItemHolder _holder = (CommentItemHolder) holder;
        _holder.commentUser.setText("Posted by " + comment.getCommentUser());
        _holder.commentContent.setText(comment.getCommentContent());
        _holder.commentDate.setText(DateDifference.dateDifference(
                DateDifference.stringToDate(comment.getCommentDate()),
                DateDifference.stringToDate(DateDifference.timeNow())
        ));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class CommentItemHolder extends RecyclerView.ViewHolder {

        TextView commentUser, commentContent, commentDate;

        CommentItemHolder(View itemView) {
            super(itemView);
            commentUser = (TextView) itemView.findViewById(R.id.comment_user);
            commentContent = (TextView) itemView.findViewById(R.id.comment_body);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
        }
    }
}

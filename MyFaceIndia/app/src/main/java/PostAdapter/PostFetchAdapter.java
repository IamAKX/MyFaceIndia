package PostAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.EventVideoActivity;
import com.akash.applications.myfaceindia.R;
import com.akash.applications.myfaceindia.UserTimeLine;
import com.akash.applications.myfaceindia.ZoomImage;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;

/**
 * Created by Amandeep on 11/1/2016.
 */

public class PostFetchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<PostFetchModel> data;


    public PostFetchAdapter(Context context, ArrayList<PostFetchModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new PostFetchViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PostFetchViewHolder _holder = (PostFetchViewHolder) holder;
        _holder.setIsRecyclable(false);
        _holder.userName.setText(data.get(position).getUserName());
        Date postDate = DateDifference.stringToDate(data.get(position).getPostTime());
        Date nowDate = DateDifference.stringToDate(DateDifference.timeNow());
        _holder.timeTxtView.setText(DateDifference.dateDifference(postDate, nowDate));
        Uri userImageUri = Uri.parse(
                UserConfig.PROFILE_PIC_URL +
                        data.get(position).getUserImage()
        );

        Glide.with(context).load(userImageUri)
                .centerCrop()
                .placeholder(R.drawable.userdp)
                .into(_holder.userImage);

        if (data.get(position).getPostType().trim().equalsIgnoreCase("2")) {
            _holder.postText.setText(data.get(position).getPostImage());
            _holder.postText.setVisibility(View.VISIBLE);
            _holder.postContent.setVisibility(View.GONE);
        }
        else
        if (data.get(position).getPostType().trim().equalsIgnoreCase("3")) {

            _holder.pVideo.setVisibility(View.VISIBLE);
            _holder.postContent.setVisibility(View.GONE);
        }
        else {
            Uri postImageUri = Uri.parse(
                    UserConfig.POST_PIC_URL +
                            data.get(position).getPostImage()
            );
            Glide.with(context).load(postImageUri)
                    //.centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.loadingimg)
                    .into(_holder.postContent);
        }
        final String numLikes = data.get(position).getNumLikes();
        if (numLikes.equalsIgnoreCase("0")) {

            _holder.numLikesText.setVisibility(View.GONE);
        } else {
            _holder.numLikesText.setVisibility(View.VISIBLE);
            _holder.numLikesText.setText(numLikes);
        }
        _holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment(context, _holder.commentEditText.getText().toString());
                _holder.commentEditText.setText("");
            }

            private void postComment(final Context context, final String s) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "comment.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                Log.i("Checking", response + " ");
                                if (response.toString().equals("success")) {
                                    Toast.makeText(context, "Comment Successfull", Toast.LENGTH_SHORT).show();

                                } else
                                    Toast.makeText(context, "Failed to comment", Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Log.i("Checking", error + " ");
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        PostFetchModel post = data.get(_holder.getAdapterPosition());
                        params.put("uid", PreferenceSaver.getUserID(context));
                        params.put("pid", post.getPostId());
                        params.put("msg", s);
                        params.put("date", DateDifference.timeNow());
                        return params;
                    }
                };

                //Adding the string request to the queue
                stringRequest.setShouldCache(false);

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().clear();

                requestQueue.add(stringRequest);

            }
        });

        _holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePost(context);
            }

            private void likePost(final Context context) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "postlike.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                Log.i("Checking", response + " ");
                                if (response.toString().equals("success")) {
                                    Toast.makeText(context, "Like Successfull", Toast.LENGTH_SHORT).show();
                                    _holder.likeButton.setImageDrawable(
                                            context.getResources().getDrawable(R.drawable.ic_thumbs_up_color_primary));
                                    _holder.numLikesText.setText(String.valueOf(Integer.parseInt(numLikes) + 1));

                                } else
                                    Toast.makeText(context, "Already Liked by you!", Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Log.i("Checking", error + " ");
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        PostFetchModel post = data.get(_holder.getAdapterPosition());
                        params.put("uid", PreferenceSaver.getUserID(context));
                        params.put("pid", post.getPostId());
                        return params;
                    }
                };

                //Adding the string request to the queue
                stringRequest.setShouldCache(false);

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().clear();

                requestQueue.add(stringRequest);
            }
        });

        final ArrayList<CommentModel> comments = new ArrayList<>();
        final CommentAdapter adapter = new CommentAdapter(comments, context);
        _holder.commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        _holder.commentRecyclerView.setAdapter(adapter);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(final String... params) {
                StringRequest request = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "commentfetch.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject obj;
                                JSONArray res;

                                try {
                                    obj = new JSONObject(response);
                                    res = obj.getJSONArray(UserConfig.COMMENT_ARRAY_NAME);
                                    for (int i = 0; i < res.length(); i++) {
                                        try {
                                            JSONObject j = res.getJSONObject(i);
                                            CommentModel comment = new CommentModel(
                                                    j.getString("uname"),
                                                    j.getString("msg"),
                                                    j.getString("time")
                                            );
                                            comments.add(comment);
                                            adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> par = new HashMap<>();
                        par.put("pid", params[0]);
                        return par;
                    }
                };

                request.setShouldCache(false);

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().clear();

                requestQueue.add(request);
                return null;
            }
        }.execute(data.get(position).getPostId());
        adapter.notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class PostFetchViewHolder extends RecyclerView.ViewHolder {

        TextView userName, timeTxtView, postText, numLikesText;
        ImageView userImage, postContent,pVideo;
        ImageButton likeButton, commentButton;
        RecyclerView commentRecyclerView;
        EditText commentEditText;

        PostFetchViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.post_item_username);
            postContent = (ImageView) itemView.findViewById(R.id.post_item_postcontent);
            timeTxtView = (TextView) itemView.findViewById(R.id.time_txt);
            userImage = (ImageView) itemView.findViewById(R.id.user_image_post);
            pVideo = (ImageView) itemView.findViewById(R.id.post_item_video_view);
            postText = (TextView) itemView.findViewById(R.id.post_item_textContent);
            numLikesText = (TextView) itemView.findViewById(R.id.number_of_likes);
            likeButton = (ImageButton) itemView.findViewById(R.id.likeButton);
            commentButton = (ImageButton) itemView.findViewById(R.id.submit_comment_button);
            commentEditText = (EditText) itemView.findViewById(R.id.commentEditText);

            postContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ZoomImage.class);
                    intent.putExtra("url", UserConfig.POST_PIC_URL +
                            data.get(getAdapterPosition()).getPostImage());
                    v.getContext().startActivity(intent);
                }
            });
            pVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Intent intent = new Intent(v.getContext(), EventVideoActivity.class);
                    intent.putExtra("imgurl", UserConfig.POST_VID_URL +
                            data.get(getAdapterPosition()).getPostImage());
                    v.getContext().startActivity(intent);*/
                    String musicURL = UserConfig.POST_VID_URL + data.get(getAdapterPosition()).getPostImage();
                    if (!musicURL.startsWith("http://") && !musicURL.startsWith("https://"))
                        musicURL = "http://" + musicURL;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(musicURL));
                    v.getContext().startActivity(browserIntent);
                }
            });

            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserTimeLine.class);
                    intent.putExtra("uImage", UserConfig.PROFILE_PIC_URL +
                            data.get(getAdapterPosition()).getUserImage());
                    intent.putExtra("uName",data.get(getAdapterPosition()).getUserName());
                    intent.putExtra("uID",data.get(getAdapterPosition()).getUserId());
                    v.getContext().startActivity(intent);
                }
            });
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserTimeLine.class);
                    intent.putExtra("uImage", UserConfig.PROFILE_PIC_URL +
                            data.get(getAdapterPosition()).getUserImage());
                    intent.putExtra("uName",data.get(getAdapterPosition()).getUserName());
                    intent.putExtra("uID",data.get(getAdapterPosition()).getUserId());
                    v.getContext().startActivity(intent);
                }
            });
            commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.comment_recycler);


        }


    }


}

package PostAdapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
 * Created by Akash on 28-10-2016.
 */

public class AdapterForTimeline extends BaseAdapter {

    ArrayList<TimelineModel> list;
    Context context;

    ArrayList<String> datelist;
    ArrayList<String> statuslist;
    private String fullJSON;

    public AdapterForTimeline(Context context) {
        list = new ArrayList<TimelineModel>();
        statuslist = new ArrayList<String>();
        datelist = new ArrayList<String>();
        this.context = context;

        new ContactServer().execute();
    }

    private void parseJSON(String response) {

        Log.i("Checking"," fullJSON "+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("statusarray");
            Log.i("Checking"," JSONResult "+result+"\n"+result.length());
            if(result==null)
            {
                Toast.makeText(context, "No status", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i=0;i<result.length();i++)
            {
                JSONObject data = result.getJSONObject(i);
                String _a = data.getString("status");
                String _b = data.getString("stime");
                statuslist.add(new String(_a));
                datelist.add(new String(_b));

                Log.i("Checking","Inserted successfully : "+statuslist.get(i)+"  /  "+datelist.get(i));
            }

        } catch (JSONException e) {

        }

        for(int i=0;i<statuslist.size();i++)
        {
            list.add(new TimelineModel(datelist.get(i),statuslist.get(i)));
        }

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
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.timeline_row,null,false);

        TextView timeTV = (TextView)row.findViewById(R.id.timeline_row_datetime);
        TextView statusTV = (TextView)row.findViewById(R.id.timeline_row_status);


        TimelineModel tem = list.get(position);
        Date startDate = DateDifference.stringToDate(tem.date);
        Date stopDate = DateDifference.stringToDate(DateDifference.timeNow());

        timeTV.setText(DateDifference.dateDifference(startDate,stopDate));
        statusTV.setText(tem.status);

        return row;
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getStatusJSON();
            return null;
        }


    }

    private void getStatusJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"statusfetch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(!response.toString().equals("failure"))
                        {

                            fullJSON = response;
                            parseJSON(response);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request

                params.put("uid", PreferenceSaver.getUserID(context));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);
    }
}

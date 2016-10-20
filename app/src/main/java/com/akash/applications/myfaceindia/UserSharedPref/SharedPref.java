package com.akash.applications.myfaceindia.UserSharedPref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akash on 19-10-2016.
 */
public class SharedPref {

    public static String SERVER_URL = "http://akashapplications.hol.es/mfi/newsvr/";
    public void saveDetail(Context cntxt,String nme,String email)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("uname",nme);
        sEdit.putString("email",email);
        sEdit.putString("loggedin","true");
        sEdit.commit();
    }
    public void saveUserid(Context cntxt,String id)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("uid",id);

        sEdit.commit();
    }
    public String getUserId(Context cntxt)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        return sp.getString("uid",null);
    }
    public String getName(Context cntxt)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        return sp.getString("uname",null);
    }
    public String getEmail(Context cntxt)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        return sp.getString("email",null);
    }
    public boolean isExist(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        return sp.contains("uname");
    }
}

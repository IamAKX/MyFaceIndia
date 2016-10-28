package HelperPackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akash on 27-10-2016.
 */

public class PreferenceSaver
{
    public static void saveDetail(Context cntxt, String username, String email, String userid,boolean loggin)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("User", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("uname",username);
        sEdit.putString("email",email);
        sEdit.putString("uid",userid);
        sEdit.putBoolean("islogin",loggin);
        sEdit.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Activity.MODE_PRIVATE);
        return sp.getString("uname","User");
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Activity.MODE_PRIVATE);
        return sp.getString("email",null);
    }

    public static String getUserID(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Activity.MODE_PRIVATE);
        return sp.getString("uid",null);
    }

    public static boolean getLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Activity.MODE_PRIVATE);
        return sp.getBoolean("islogin",false);
    }
}

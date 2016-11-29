package HelperPackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akash on 27-10-2016.
 */

public class PreferenceSaver
{
    public static void saveDetail(Context cntxt, String username, String email,String imageName, String userid,boolean loggin)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("User", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("uname",username);
        sEdit.putString("email",email);
        sEdit.putString("img",imageName);
        sEdit.putString("uid",userid);
        sEdit.putBoolean("islogin",loggin);
        sEdit.commit();
    }

    public static void saveImg(Context cntxt, String imageName)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("User", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("img",imageName);
        sEdit.commit();
    }
    public static String getImg(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Activity.MODE_PRIVATE);
        return sp.getString("img","default.jpg");
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





    public static void saveProfile(Context cntxt, boolean savedState, String fname, String lname, int day, int month, int year, int gender,int intrest, int country, String city, String address)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("savedstate",savedState);
        sEdit.putString("fname",fname);
        sEdit.putString("lname",lname);
        sEdit.putInt("day",day);
        sEdit.putInt("month",month);
        sEdit.putInt("year",year);
        sEdit.putInt("gender",gender);
        sEdit.putInt("intrest",intrest);
        sEdit.putInt("country",country);
        sEdit.putString("city",city);
        sEdit.putString("address",address);
        sEdit.commit();
    }

    public static boolean getSaveState(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getBoolean("savedstate",false);
    }

    public static String getFname(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getString("fname",null);
    }

    public static String getLname(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getString("lname",null);
    }
    public static String getCity(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getString("city",null);
    }
    public static String getAddress(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getString("address",null);
    }
    public static int getMonth(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("month",0);
    }
    public static int getYear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("year",0);
    }
    public static int getDay(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("day",0);
    }
    public static int getGender(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("gender",0);
    }
    public static int getIntrest(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("intrest",0);
    }
    public static int getCountry(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.getInt("country",0);
    }
    public static boolean isExist(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        return sp.contains("savedstate");
    }
    public static void deleteUserProfile(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

//====================================OTHER DETAILS=============================================================

    public static void saveProfileOtherDetails(Context cntxt, String work, String school, String web, String google, String facebook, String twitter, String bio)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("work",work);
        sEdit.putString("school",school);
        sEdit.putString("web",web);
        sEdit.putString("google",google);
        sEdit.putString("facebook",facebook);
        sEdit.putString("twitter",twitter);
        sEdit.putString("bio",bio);
        sEdit.commit();
    }

    public static String getWork(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("work",null);
    }
    public static String getSchool(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("school",null);
    }
    public static String getWeb(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("web",null);
    }
    public static String getGoogle(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("google",null);
    }
    public static String getFacebook(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("facebook",null);
    }
    public static String getTwitter(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("twitter",null);
    }
    public static String getBio(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserProfileOtherDetails", Activity.MODE_PRIVATE);
        return sp.getString("bio",null);
    }


    //=============================NOTIFICATION SETTINGS==========================================================


    public static void saveLikeNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("likenoti",s);
        sEdit.commit();
    }
    public static void saveCommentNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("commentnoti",s);
        sEdit.commit();
    }
    public static void saveMessageNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("messagenoti",s);
        sEdit.commit();
    }
    public static void saveChatNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("chatnoti",s);
        sEdit.commit();
    }
    public static void saveFriendNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("friendnoti",s);
        sEdit.commit();
    }
    public static void saveGroupNotification(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("groupnoti",s);
        sEdit.commit();
    }
    public static void saveNotificationSound(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("notisound",s);
        sEdit.commit();
    }
    public static void saveEmailComment(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("emailcomment",s);
        sEdit.commit();
    }
    public static void saveEmailLike(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("emaillike",s);
        sEdit.commit();
    }
    public static void saveEmailFriendship(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("emailfriendship",s);
        sEdit.commit();
    }
    public static void saveEmailGroupInvite(Context context,boolean s) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putBoolean("emailgroupinvite",s);
        sEdit.commit();
    }


    public static boolean getLikeNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("likenoti",false);
    }
    public static boolean getCommentNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("commentnoti",false);

    }
    public static boolean getMessageNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("messagenoti",false);

    }
    public static boolean getChatNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("chatnoti",false);

    }
    public static boolean getFriendNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("friendnoti",false);
    }
    public static boolean getGroupNotification(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("groupnoti",false);

    }
    public static boolean getNotificationSound(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("notisound",false);

    }
    public static boolean getEmailComment(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("emailcomment",false);

    }
    public static boolean getEmailLike(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("emaillike",false);

    }
    public static boolean getEmailFriendship(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("emailfriendship",false);

    }
    public static boolean getEmailGroupInvite(Context context) {
        SharedPreferences sp = context.getSharedPreferences("NotificationSettings", Activity.MODE_PRIVATE);
        return sp.getBoolean("emailgroupinvite",false);

    }


}

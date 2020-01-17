package vn.funtap.funtapsdklite.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static final String PREFERENCE_NAME = "sp_mobgame";
    public static SharedPreferences getSharedPreferences(Context context) {
        if(context!=null){
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }else{
            return null;
        }
    }

    public static boolean save(Context context, String key, String value) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }catch (Exception e){
            return false;
        }
    }

    public static String getString(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            return sharedPreferences.getString(key, "");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void remove(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

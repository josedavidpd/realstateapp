package com.st.jdpolonio.inmobiliapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.st.jdpolonio.inmobiliapp.models.User;

public class Util {

    public static void setData(Context ctx, String token, String user_id, String user_email, String user_name, String user_photo) {

        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.putString("idUser", user_id);
        editor.putString("emailUser", user_email);
        editor.putString("nombreUser", user_name);
        editor.putString("fotoUser", user_photo);
        editor.commit();

    }

    public static User getUserLogged(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String id = prefs.getString("idUser",null);
        String email= prefs.getString("emailUser",null);
        String name= prefs.getString("nombreUser",null);
        String picture = prefs.getString("fotoUser",null);
        User user = new User(id,email,name,picture);
        return user;
    }

    public static String getToken(Context ctx) {

        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getString("token", null);

    }

    public static String getUserId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getString("idUser", null);
    }

    public static String getEmailUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getString("emailUser", null);

    }

    public static String getNombreUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getString("nombreUser", null);
    }

    public static String getPhotoUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getString("fotoUser", null);
    }

    public static void clearSharedPreferences(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}

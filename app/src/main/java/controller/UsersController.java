package controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.Users;

public class UsersController {
    private static List<Users> users;

    public static List<Users> getUsers(String response ) throws  JSONException{
        users = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        Users user;

        for(int i = 0 ; i < array.length() ; i++) {
            JSONObject object = array.getJSONObject(i);
            user = converteUser(object);
            users.add(user);
       }
        return users;
    }

    public static Users getUser(String response) throws JSONException {
        JSONObject object = new JSONObject(response);

        return converteUser(object);
    }

    private static Users converteUser(JSONObject jsonObject) throws JSONException {

        return new Users(
                jsonObject.getString("_id"),
                jsonObject.getString("name"),
                jsonObject.getString("email"),
                jsonObject.getString("phone"),
                jsonObject.getString("info"),
                jsonObject.getString("typeuser"),
                jsonObject.getString("username")
        );
    }
}

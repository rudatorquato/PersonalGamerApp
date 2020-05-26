package controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.Users;

public class UsersController {
    private static List<Users> user;

    public static List<Users> getUsers(String response ) throws  JSONException{
        Log.d("RESULT USERS", response);

        user = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        Users users;

        for(int i = 0 ; i < array.length() ; i++) {
            JSONObject object = array.getJSONObject(i);
            users = converteUser(object);
            user.add(users);
       }
        return user;
    }
    public static Users getUser(String response) throws JSONException {
        JSONArray array = new JSONArray(response);
        Users user;
        JSONObject object = array.getJSONObject(0);
        user = converteUser(object);

        return user;
    }

    private static Users converteUser(JSONObject jsonObject) throws JSONException {

        return new Users(
                jsonObject.getInt("id"),
                jsonObject.getString("Name"),
                jsonObject.getString("Email"),
                jsonObject.getString("Telephone"),
                jsonObject.getString("Info"),
                jsonObject.getString("TypeUser"),
                jsonObject.getString("Username")
        );
    }
}

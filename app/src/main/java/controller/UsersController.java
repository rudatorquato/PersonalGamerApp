package controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.User;

public class UsersController {
    private static List<User> users;

    public static List<User> getUsers(String response ) throws  JSONException{
        users = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        User user;

        for(int i = 0 ; i < array.length() ; i++) {
            JSONObject object = array.getJSONObject(i);
            user = converteUser(object);
            users.add(user);
       }
        return users;
    }

    public static User getUser(String response) throws JSONException {
        JSONObject object = new JSONObject(response);

        return converteUser(object);
    }

    private static User converteUser(JSONObject jsonObject) throws JSONException {

        return new User(
                jsonObject.getString("_id"),
                jsonObject.getString("name"),
                jsonObject.getString("email"),
                jsonObject.getString("phone"),
                jsonObject.getString("info"),
                jsonObject.getString("typeuser"),
                jsonObject.getString("username"),
                jsonObject.getInt("exp")
        );
    }
}

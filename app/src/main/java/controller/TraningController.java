package controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.Traning;

public class TraningController {

    private static List<Traning> traning;

    public static List<Traning> getTraning(String response ) throws JSONException{
        Log.d("RESULT USERS", response);

        traning = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        Traning traningDif;

        for(int i = 0 ; i < array.length() ; i++) {
            JSONObject object = array.getJSONObject(i);
            traningDif = converteTraning(object);
            traning.add(traningDif);
        }
        return traning;
    }

    public static Traning getTraningEsp(String response) throws JSONException {
        JSONArray array = new JSONArray(response);
        Traning traning;
        JSONObject object = array.getJSONObject(0);
        traning = converteTraning(object);

        return traning;
    }

    private static Traning converteTraning(JSONObject jsonObject) throws JSONException {

        return new Traning(
                jsonObject.getString("NameTraning"),
                jsonObject.getString("Place"),
                jsonObject.getString("Exercise"),
                jsonObject.getInt("Sequence"),
                jsonObject.getInt("Series"),
                jsonObject.getInt("Repetition"),
                jsonObject.getInt("Charge")

        );
    }
}

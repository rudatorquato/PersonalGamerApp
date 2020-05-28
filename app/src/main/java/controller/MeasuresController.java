package controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.Measures;


public class MeasuresController {

    public static Measures getMeasures(String response) throws JSONException{
        JSONObject object = new JSONObject(response);
        JSONObject measures = object.getJSONObject("measures");

        return converteMeasures(measures);
    }

    private static Measures converteMeasures(JSONObject jsonObject) throws JSONException {

        return new Measures(
                jsonObject.getLong("weight"),
                jsonObject.getLong("stature"),
                jsonObject.getLong("shoulder"),
                jsonObject.getLong("inspired_chest"),
                jsonObject.getLong("left_relaxed_arm"),
                jsonObject.getLong("right_relaxed_arm"),
                jsonObject.getLong("left_thigh"),
                jsonObject.getLong("right_thigh"),
                jsonObject.getLong("left_forearm"),
                jsonObject.getLong("right_forearm"),
                jsonObject.getLong("left_contracted_arm"),
                jsonObject.getLong("right_contracted_arm"),
                jsonObject.getLong("waist"),
                jsonObject.getLong("abdomen"),
                jsonObject.getLong("hip"),
                jsonObject.getLong("left_leg"),
                jsonObject.getLong("right_leg")
        );
    }
}

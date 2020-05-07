package controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modelo.Measures;


public class MeasuresController {

    private static List<Measures> measures;

    public static List<Measures> getMeasures(String response ) throws JSONException{
        Log.d("RESULT USERS", response);

        measures = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        Measures measuresDif;

        for(int i = 0 ; i < array.length() ; i++) {
            JSONObject object = array.getJSONObject(i);
            measuresDif = converteMeasures(object);
            measures.add(measuresDif);
        }
        return measures;
    }

    public static Measures getMeasuresEsp(String response) throws JSONException {
        JSONArray array = new JSONArray(response);
        Measures measures;
        JSONObject object = array.getJSONObject(0);
        measures = converteMeasures(object);

        return measures;
    }

    private static Measures converteMeasures(JSONObject jsonObject) throws JSONException {

        return new Measures(
                jsonObject.getLong("Weight"),
                jsonObject.getLong("Stature"),
                jsonObject.getLong("Shoulder"),
                jsonObject.getLong("InspiredChest"),
                jsonObject.getLong("LeftRelaxedArm"),
                jsonObject.getLong("RightRelaxedArm"),
                jsonObject.getLong("LeftThigh"),
                jsonObject.getLong("RightThigh"),
                jsonObject.getLong("LeftForearm"),
                jsonObject.getLong("RightForearm"),
                jsonObject.getLong("LeftContractedArm"),
                jsonObject.getLong("RightContractedArm"),
                jsonObject.getLong("Waist"),
                jsonObject.getLong("Abdomen"),
                jsonObject.getLong("Hip"),
                jsonObject.getLong("LeftLeg"),
                jsonObject.getLong("RightLeg")
        );
    }
}

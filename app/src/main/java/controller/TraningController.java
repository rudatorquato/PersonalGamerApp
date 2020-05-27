package controller;

import org.json.JSONException;
import org.json.JSONObject;

import modelo.Training;

public class TraningController {

    public static Training getTraning(String response) throws JSONException {
        JSONObject object = new JSONObject(response);

        return converteQrCode(object);
    }

    private static Training converteQrCode(JSONObject jsonObject) throws JSONException {

        return new Training(
                jsonObject.getString("image"),
                jsonObject.getString("training"),
                jsonObject.getString("place"),
                jsonObject.getString("exercise"),
                jsonObject.getInt("sequence"),
                jsonObject.getInt("series"),
                jsonObject.getInt("repetitions"),
                jsonObject.getInt("charge"));
    }
}

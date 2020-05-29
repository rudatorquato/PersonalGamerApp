package controller;

import org.json.JSONException;
import org.json.JSONObject;

import modelo.Training;

public class TraningController {

    public static Training getTraning(String response) throws JSONException {
        JSONObject object = new JSONObject(response);

        return converteTraining(object);
    }

    public static Training getTranings(String response) throws JSONException{
        JSONObject object = new JSONObject(response);
        JSONObject trainings = object.getJSONObject("training");

        return converteTraining(trainings);
    }

    private static Training converteTraining(JSONObject jsonObject) throws JSONException {

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

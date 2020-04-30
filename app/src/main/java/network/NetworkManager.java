package network;

import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import interfaces.NetworkObserver;

public class NetworkManager {
    private static final String TAG = Principal.class.getSimpleName();
    public static final String ACCESS_TOKEN = "";

    private NetworkObserver networkObserver;

    public void setNetworkObserver(NetworkObserver networkObserver) {
        this.networkObserver = networkObserver;
    }

    public void post(final Map<String, String> params, String url){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> networkObserver.doOnPost(response),
                error -> networkObserver.doOnError(error.getMessage())){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", ACCESS_TOKEN);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        ConnectionController.getInstance().addToRequestQueue(request);
    }

    public void get(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> networkObserver.doOnGet(response),
                error -> networkObserver.doOnError(error.getMessage()));
        ConnectionController.getInstance().addToRequestQueue(request);
    }

    public void patch(final Map<String, String> params, String url){
        StringRequest request = new StringRequest(Request.Method.PATCH, url, response -> {
        }, error -> networkObserver.doOnError(error.getMessage())) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        ConnectionController.getInstance().addToRequestQueue(request);
    }

    public void del(String url){
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> { },
                error -> networkObserver.doOnError(error.getMessage()));
        ConnectionController.getInstance().addToRequestQueue(request);
    }

    public void postArray (JSONObject jsonObject, String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println("Show");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        ConnectionController.getInstance().addToRequestQueue(request);
    }
}

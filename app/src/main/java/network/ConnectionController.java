package network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ConnectionController extends Application {
    public static final String TAG = ConnectionController.class.getSimpleName();

    private static ConnectionController connectionController;


    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionController = this;
    }

    public static synchronized ConnectionController getInstance(){
        return connectionController;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
}
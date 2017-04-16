package network;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LENOVO on 2/2/2017.
 */

public class ConnectionHandler {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*90;
    public static final String BASE_URL =  "http://yukkajian.adeilhamfajri.id/api/",IMAGE_URL =  "http://103.252.101.105/uploads/users/", response_message_success = "success", response_message_error = "error"
            ,response_data = "data",response_message = "message", response_status = "status", response_pagination = "pagination", BaseURLImage = "http://103.252.101.105/";;
    public static int post_method = Request.Method.POST, get_method = Request.Method.GET;
    Context context;
    public ConnectionHandler(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }
    public void MakeConnection(int method,String route, JSONObject prop, final JsonCallback jsonCallback, String title, String message){
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, BASE_URL + route, prop, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if(response.getString(response_status).equals(response_message_success)) {
                        String data = response.getString(response_data), pagination = "";
                        if (response.has(response_pagination))
                            pagination = response.getString(response_pagination);
                        JSONObject fromServer = new JSONObject();
                        fromServer.put(response_data, data);
                        fromServer.put(response_pagination, pagination);
                        jsonCallback.Done(fromServer, response.getString(response_status));
                    }
                    else
                        jsonCallback.Done(null, response.getString(response_message));
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonCallback.Done(null,e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                jsonCallback.Done(null,error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context, null);
        RetryPolicy policy = new DefaultRetryPolicy(CONNECTION_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }
    public void MakeConnection(int method,String route, JSONObject prop, final JsonCallback jsonCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, BASE_URL + route, prop, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    String data = response.getString(response_data), pagination = "";
                    if(response.has(response_pagination))
                        pagination = response.getString(response_pagination);
                    JSONObject fromServer = new JSONObject();
                    fromServer.put(response_data,data);
                    fromServer.put(response_pagination,pagination);
                    jsonCallback.Done(fromServer,response.getString(response_status));
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonCallback.Done(null,e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                jsonCallback.Done(null,error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context, null);
        RetryPolicy policy = new DefaultRetryPolicy(CONNECTION_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }
}

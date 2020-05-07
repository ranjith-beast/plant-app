package com.example.plant;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forecast {
    private static final String PredictionUrl="http://192.168.43.152:5000/getProduction";
    public String prediction;
    public String Forecast(final String district_name, final String crop_name, Context context){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,PredictionUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try
                        {
                            jsonResponse = new JSONObject(response);
                            prediction=jsonResponse.getString("prediction");
                            Log.d("Prediction",prediction);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  loading.dismiss();
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("district", district_name);
                params.put("crop",crop_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return prediction;
    }
}

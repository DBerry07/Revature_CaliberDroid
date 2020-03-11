package com.revature.caliberdroid.data.api

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class VolleyJsonObjectRequest(
    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject?>?,
    errorListener: Response.ErrorListener?
) : JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
        var networkResponse = response
        try {
            if (networkResponse.data.size == 0) {
                val responseData = "{}".toByteArray(charset("UTF8"))
                networkResponse = NetworkResponse(
                    networkResponse.statusCode,
                    responseData,
                    networkResponse.notModified,
                    networkResponse.networkTimeMs,
                    networkResponse.allHeaders
                )
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return super.parseNetworkResponse(networkResponse)
    }
//    int statusCode,
//    byte[] data,
//    boolean notModified,
//    long networkTimeMs,
//    List<Header> allHeaders)

//    int statusCode, byte[] data, Map<String, String> headers, boolean notModified)
}
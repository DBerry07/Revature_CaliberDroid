package com.revature.caliberdroid.data.api

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import java.io.UnsupportedEncodingException

class VolleyJsonArrayRequest(
    method: Int,
    url: String?,
    jsonRequest: JSONArray?,
    listener: Response.Listener<JSONArray?>?,
    errorListener: Response.ErrorListener?
) : JsonArrayRequest(method, url, jsonRequest, listener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONArray> {
        var networkResponse = response
        try {
            if (networkResponse.data.size == 0) {
                val responseData = "[]".toByteArray(charset("UTF8"))
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
package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Batch
import org.json.JSONArray
import org.json.JSONObject

object JSONParser {

    fun parseBatch(response: JSONArray): Batch {

        val firstBatch: JSONObject = response.get(0) as JSONObject

        val batch = Batch(firstBatch.getLong("batchId"))

        return batch
    }

}
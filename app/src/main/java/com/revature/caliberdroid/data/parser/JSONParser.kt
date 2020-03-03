package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Batch
import org.json.JSONArray

object JSONParser {

    fun parseBatch(response: JSONArray): List<Batch> {

        val batchList = ArrayList<Batch>()

        var batch: Batch
        val length = response.length() - 1
        for (i in 0 .. length) {
            response.getJSONObject(i).apply {
                batch = Batch(getLong("batchId"), getString("trainingName"), getString("trainingType"), getString("skillType"), getString("trainer"), getString("coTrainer"), getLong("locationId"), getString("location"), getLong("startDate"), getLong("endDate"), getInt("goodGrade"), getInt("passingGrade"), getInt("weeks"))
            }

            batchList.add(batch)
        }

        return batchList
    }

}
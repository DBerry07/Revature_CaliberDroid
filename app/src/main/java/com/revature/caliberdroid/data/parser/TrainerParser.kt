package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Trainer
import org.json.JSONArray

object TrainerParser {

    fun parseTrainer(response: JSONArray):ArrayList<Trainer>{
        val trainers: ArrayList<Trainer> = ArrayList()

        for(i in 0 until response.length()){
            var trainer = response.getJSONObject(i)
            trainers.add( Trainer(
                trainer.getLong("trainerId"),
                trainer.getString("name"),
                trainer.getString("title"),
                trainer.getString("email"),
                trainer.getString("tier"),
                trainer.getString("password")
            ))
        }
        return trainers
    }
}
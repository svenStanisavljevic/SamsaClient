package hr.tvz.samsa.rest

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mashape.unirest.http.Unirest
import hr.tvz.samsa.*

private val baseUrl = "http://localhost:8080"

class RestClient {

    fun listAllTopics(address: String): List<String> {
        val response = Unirest
            .get("$baseUrl/topic")
            .header("address", address)
            .asJson().body.toString()
        println(response)
        return response
            .removeSurrounding("[", "]")
            .split(",")
            .map { it.trim() }
    }

    fun describeTopic(address: String, name: String): DescribedTopic {
        val response = Unirest
            .get("$baseUrl/topic/$name")
            .header("address", address)
            .asJson().body.toString()
        println(response)
        val g = Gson()
        return g.fromJson(response, DescribedTopic::class.java)
    }

    fun describeTopicConfig(address: String, name: String, values: Boolean): String {
        val response = Unirest
            .get("$baseUrl/topic/config/$name")
            .header("address", address)
            .header("values", values.toString())
            .asString().body.toString()
        println(response)
        return response
    }

    fun updateTopicConfig(address: String, name: String, configUpdate: TopicConfigUpdate) {
        val gson = Gson()
        val json = JsonObject().apply {
            addProperty("configName", configUpdate.configName)
            addProperty("configValue", configUpdate.configValue)
        }
        val jsonString = gson.toJson(json)

        val response = Unirest
            .post("$baseUrl/topic/config/$name")
            .header("Content-type", "application/json")
            .header("address", address)
            .body(jsonString)
            .asJson().body.toString()
        println(response)
    }

    fun createTopic(address: String, topic: DescribedTopic) {
        val gson = Gson()
        val json = JsonObject().apply {
            addProperty("topicName", topic.topicName)
            addProperty("partitionCount", topic.partitionCount)
            addProperty("replicationFactor", topic.replicationFactor)
        }
        val jsonString = gson.toJson(json)

        val response = Unirest
            .post("$baseUrl/topic")
            .header("Content-type", "application/json")
            .header("address", address)
            .body(jsonString)
            .asJson().body.toString()
        println(response)
    }

    fun deleteTopic(address: String, topic: String) {
        val response = Unirest
            .delete("$baseUrl/topic/$topic")
            .header("address", address)
            .asJson().body.toString()
        println(response)
    }

    fun produceTopic(address: String, topic: String, record: ProduceRequest) {
        val gson = Gson()
        val json = JsonObject().apply {
            addProperty("key", record.key)
            addProperty("value", record.value)
            addProperty("ack", record.ack)
            addProperty("compression", record.compression)
        }
        val jsonString = gson.toJson(json)

        val response = Unirest
            .post("$baseUrl/topic/produce/$topic")
            .header("Content-type", "application/json")
            .header("address", address)
            .body(jsonString)
            .asJson().body.toString()
        println(response)
    }

    fun consumeTopic(address: String, topic: String, request: ConsumeRequest): List<ConsumedRecord> {
        val gson = Gson()
        val json = JsonObject().apply {
            addProperty("direction", request.direction)
            addProperty("partition", request.partition)
        }
        val jsonString = gson.toJson(json)
        val response = Unirest
            .post("$baseUrl/topic/consume/$topic")
            .header("Content-type", "application/json")
            .header("address", address)
            .body(jsonString)
            .asJson().body.toString()
        println(response)
        val listType = object : TypeToken<ArrayList<ConsumedRecord>>() {}.type
        return gson.fromJson(response, listType)
    }

    fun describeCluster(address: String): List<DescribedBroker> {
        val gson = Gson()
        val response = Unirest
            .get("$baseUrl/cluster")
            .header("address", address)
            .asJson().body.toString()
        val listType = object : TypeToken<ArrayList<DescribedBroker>>() {}.type
        return gson.fromJson(response, listType)
    }

    fun listAllGroups(address: String): List<DescribedGroup> {
        val gson = Gson()
        val response = Unirest
            .get("$baseUrl/consumer")
            .header("address", address)
            .asJson().body.toString()
        val listType = object : TypeToken<ArrayList<DescribedGroup>>() {}.type
        return gson.fromJson(response, listType)
    }
}
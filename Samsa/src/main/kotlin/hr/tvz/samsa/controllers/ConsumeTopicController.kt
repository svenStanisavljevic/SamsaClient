package hr.tvz.samsa.controllers

import hr.tvz.samsa.ConsumeRequest
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.IOException

class ConsumeTopicController {

    private val rest = RestClient()

    @FXML
    private var topicName = Label()

    @FXML
    private var groupId = TextField()

    @FXML
    private var refresh = TextField()

    @FXML
    private var offsetReset = ChoiceBox<String>()

    @FXML
    private var partition = ChoiceBox<String>()

    @Override
    fun initialize() {
        populateData()
    }

    @FXML
    fun consume() {
        SamsaApplication.consumeRequest = ConsumeRequest(
            offsetReset.value,
            partition.value,
        )
        SamsaApplication.refreshRate = refresh.text
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("consume.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 500.0, 700.0)
            val newStage = Stage()
            newStage.scene = scene
            SamsaApplication.consumeId++
            if (SamsaApplication.consumeId == 10) {
                SamsaApplication.consumeId = 1
            }
            SamsaApplication.consumeMap[SamsaApplication.consumeId] = true
            newStage.title = "Samsa Consume ${SamsaApplication.consumeId}"
            newStage.show()
            newStage.setOnHiding { event ->
                println("Closing stage: ${newStage.title}")
                val numChar = newStage.title.toString().last()
                val num = numChar.toInt()
                SamsaApplication.consumeMap[num] = false
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun populateData() {
        refresh.text = "10000"
        topicName.text = SamsaApplication.selectedTopic
        val topic = rest.describeTopic(SamsaApplication.selectedClusterAddress, SamsaApplication.selectedTopic)
        for (i in 0 until topic.partitionCount) {
            partition.items.add(i.toString())
        }
        offsetReset.items.addAll("start", "end")
        offsetReset.value = "end"
        partition.value = "0"
    }
}
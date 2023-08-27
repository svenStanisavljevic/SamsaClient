package hr.tvz.samsa.controllers

import hr.tvz.samsa.ProduceRequest
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.IOException

class ProduceTopicController {

    private val rest = RestClient()

    @FXML
    private var topicName = Label()

    @FXML
    private var key = TextField()

    @FXML
    private var value = TextArea()

    @FXML
    private var acknowledgment = ChoiceBox<String>()

    @FXML
    private var compression = ChoiceBox<String>()


    @Override
    fun initialize() {
        populateData()
    }

    @FXML
    fun produce() {
        rest.produceTopic(
            SamsaApplication.selectedClusterAddress,
            SamsaApplication.selectedTopic,
            ProduceRequest(
                key.text,
                value.text,
                acknowledgment.value,
                compression.value
            )
        )
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("produceTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun populateData() {
        topicName.text = SamsaApplication.selectedTopic
        acknowledgment.items.addAll("0", "1", "all")
        acknowledgment.value = "0"
        compression.items.addAll("none", "gzip", "snappy", "lz4")
        compression.value = "none"
    }

}
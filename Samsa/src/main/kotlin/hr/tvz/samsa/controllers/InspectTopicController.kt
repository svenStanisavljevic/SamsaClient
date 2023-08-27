package hr.tvz.samsa.controllers

import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.TopicConfigUpdate
import hr.tvz.samsa.rest.RestClient
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import java.io.IOException

class InspectTopicController {

    private val rest = RestClient()

    @FXML
    private var topicName = Label()

    @FXML
    private var configText = Text()

    @FXML
    private var choiceConfig = ChoiceBox<String>()

    @FXML
    private var valueConfig = TextField()

    @Override
    fun initialize() {
        configText.textAlignment = TextAlignment.LEFT
        topicName.text = SamsaApplication.selectedTopic
        configText.text = rest.describeTopicConfig(
            SamsaApplication.selectedClusterAddress, SamsaApplication.selectedTopic, true)
        val configsRaw = rest.describeTopicConfig(
            SamsaApplication.selectedClusterAddress, SamsaApplication.selectedTopic, false)
            .split("\n")
        choiceConfig.items.addAll(configsRaw)
    }


    @FXML
    fun updateConfig() {
        val configUpdate = TopicConfigUpdate(choiceConfig.value, valueConfig.text)
        rest.updateTopicConfig(
            SamsaApplication.selectedClusterAddress, SamsaApplication.selectedTopic, configUpdate)
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("inspectTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    fun launchProduce() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("produceTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    fun launchConsume() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("consumeTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    fun delete() {
        rest.deleteTopic(SamsaApplication.selectedClusterAddress, SamsaApplication.selectedTopic)
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("listTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    fun calculate() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("calculator.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 500.0, 700.0)
            val newStage = Stage()
            newStage.scene = scene
            newStage.title = "Samsa Calculator"
            newStage.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}
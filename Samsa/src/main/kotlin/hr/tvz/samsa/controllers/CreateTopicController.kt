package hr.tvz.samsa.controllers

import hr.tvz.samsa.DescribedTopic
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import java.io.IOException

class CreateTopicController {

    private val rest = RestClient()

    @FXML
    private lateinit var topicName: TextField

    @FXML
    private lateinit var partitionCount: ChoiceBox<Int>

    @FXML
    private lateinit var replicationFactor: ChoiceBox<Int>

    @Override
    fun initialize() {
        populateChoiceBoxes()
    }

    @FXML
    fun create() {
        val topic = DescribedTopic(
            topicName.text,
            partitionCount.value,
            replicationFactor.value,
        )
        rest.createTopic(SamsaApplication.selectedClusterAddress, topic)

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
    fun back() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("listTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun populateChoiceBoxes() {
        val brokerCount = calculateNumBrokers()
        for (i in 1..brokerCount) {
            replicationFactor.items.add(i)
        }
        for (i in 1..255) {
            partitionCount.items.add(i)
        }
    }

    private fun calculateNumBrokers(): Int {
        val count = SamsaApplication.selectedClusterAddress.count {
            it == ','
        }
        return count + 1
    }

}
package hr.tvz.samsa.controllers

import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import hr.tvz.samsa.util.YamlParser
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.scene.text.Text
import java.io.IOException

class SelectClusterController {

    private val connectionFile = "src/main/resources/connections.yaml"


    @FXML
    private var clusterSelect = ChoiceBox<String>()

    @FXML
    private var createName = TextField()

    @FXML
    private var createAddress = TextField()

    @FXML
    private var confirmationText = Text()

    @Override
    fun initialize() {
        confirmationText.isVisible = false
        updateChoiceBox()
    }

    @FXML
    fun create() {
        YamlParser().writeConnection(connectionFile, createName.text, createAddress.text)
        updateChoiceBox()
        confirmationText.isVisible = true
    }

    @FXML
    fun connect() {
        val connections = YamlParser().readConnections(connectionFile)
        connections.forEach {
            if (it.name == clusterSelect.value) {
                SamsaApplication.selectedClusterAddress = it.address
                SamsaApplication.selectedClusterName = it.name
            }
        }
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("cluster.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateChoiceBox() {
        clusterSelect.items.clear()
        val connections = YamlParser().readConnections(connectionFile)
        connections.forEach {
            clusterSelect.items.add(it.name)
            println(it.name)
            println(it.address)
        }
    }

}
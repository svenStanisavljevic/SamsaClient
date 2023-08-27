package hr.tvz.samsa.controllers

import hr.tvz.samsa.SamsaApplication
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import java.io.IOException

class MenuController {

    @Override
    fun initialize() {
    }

    @FXML
    fun launchTopics() {
        SamsaApplication.selectedTopic = ""
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
    fun launchConsumerGroups() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("listConsumerGroup.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    fun launchClusters() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("cluster.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
package hr.tvz.samsa.controllers

import hr.tvz.samsa.DescribedTopic
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import java.io.IOException

class ListTopicController {

    private val rest = RestClient()
    private lateinit var topics: MutableList<String>
    private var describedTopics: MutableList<DescribedTopic> = mutableListOf()

    @FXML
    private var topicTable = TableView<DescribedTopic>()

    @FXML
    private var topicName = TableColumn<DescribedTopic, String>("topicName")

    @FXML
    private var partitionCount = TableColumn<DescribedTopic, Int>("partitionCount")

    @FXML
    private var replicationFactor = TableColumn<DescribedTopic, Int>("replicationFactor")

    @Override
    fun initialize() {
        updateData()
        topicClickListener()
    }

    @FXML
    fun launchCreate() {
        try {
            val loader = FXMLLoader(SamsaApplication::class.java.getResource("createTopic.fxml"))
            val parent = loader.load<Parent>()
            val scene = Scene(parent, 1200.0, 700.0)
            SamsaApplication.getStage().scene = scene
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @FXML
    private fun topicClickListener() {
        topicTable.rowFactory = Callback {
            val row = TableRow<DescribedTopic>()
            row.setOnMouseClicked { event ->
                if (event.clickCount == 2 && (!row.isEmpty)) {
                    SamsaApplication.selectedTopic = row.item.topicName
                    try {
                        val loader = FXMLLoader(SamsaApplication::class.java.getResource("inspectTopic.fxml"))
                        val parent = loader.load<Parent>()
                        val scene = Scene(parent, 1200.0, 700.0)
                        SamsaApplication.getStage().scene = scene
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            row
        }
    }

    private fun updateData() {
        topics = rest.listAllTopics(SamsaApplication.selectedClusterAddress).toMutableList()
        topics.forEach {
            describedTopics.add(rest.describeTopic(SamsaApplication.selectedClusterAddress, it.removeSurrounding("\"")))
        }
        val observableDescribedTopics = FXCollections.observableArrayList(describedTopics)
        topicName.cellValueFactory = PropertyValueFactory("topicName")
        partitionCount.cellValueFactory = PropertyValueFactory("partitionCount")
        replicationFactor.cellValueFactory = PropertyValueFactory("replicationFactor")
        topicTable.items.addAll(observableDescribedTopics)
    }

}
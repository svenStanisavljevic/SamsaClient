package hr.tvz.samsa.controllers

import hr.tvz.samsa.ConsumeRequest
import hr.tvz.samsa.ConsumedRecord
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

class ConsumeController {

    private val rest = RestClient()

    @FXML
    private var topicName = Label()

    @FXML
    private var table = TableView<ConsumedRecord>()

    @FXML
    private var value = TableColumn<ConsumedRecord, String>("value")

    @FXML
    private var offset = TableColumn<ConsumedRecord, Long>("offset")


    @Override
    fun initialize() {
        val stageId = SamsaApplication.consumeId
        topicName.text = SamsaApplication.selectedTopic
        val refreshRate = SamsaApplication.refreshRate.toLong()
        updateData()
        Thread {
            var currentMs = System.currentTimeMillis()
            while (SamsaApplication.consumeMap[stageId] == true) {
                if (System.currentTimeMillis() - refreshRate >= currentMs) {
                    currentMs = System.currentTimeMillis()
                    updateData()
                }
            }
        }.start()
    }

    private fun updateData() {
        val list = rest.consumeTopic(
            SamsaApplication.selectedClusterAddress,
            SamsaApplication.selectedTopic,
            SamsaApplication.consumeRequest
        )
        val observableList = FXCollections.observableArrayList(list)
        table.items.clear()
        value.cellValueFactory = PropertyValueFactory("value")
        offset.cellValueFactory = PropertyValueFactory("offset")
        table.items.addAll(observableList)
        table.refresh()
    }
}
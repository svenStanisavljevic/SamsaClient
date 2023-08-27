package hr.tvz.samsa.controllers

import hr.tvz.samsa.DescribedGroup
import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory


class ListConsumerGroupController {

    private val rest = RestClient()
    private var groups = mutableListOf<DescribedGroup>()

    @FXML
    private var groupTable = TableView<DescribedGroup>()

    @FXML
    private var groupId = TableColumn<DescribedGroup, String>("groupId")

    @FXML
    private var state = TableColumn<DescribedGroup, String>("state")

    @FXML
    private var memberId = TableColumn<DescribedGroup, String>("memberId")

    @FXML
    private var host = TableColumn<DescribedGroup, String>("host")

    @FXML
    private var topic = TableColumn<DescribedGroup, String>("topic")

    @FXML
    private var partition = TableColumn<DescribedGroup, Int>("partition")

    @FXML
    private var offset = TableColumn<DescribedGroup, Long>("offset")

    @Override
    fun initialize() {
        updateData()
    }

    private fun updateData() {
        groups = rest.listAllGroups(SamsaApplication.selectedClusterAddress).toMutableList()
        val observable = FXCollections.observableArrayList(groups)
        groupId.cellValueFactory = PropertyValueFactory("groupId")
        state.cellValueFactory = PropertyValueFactory("state")
        memberId.cellValueFactory = PropertyValueFactory("memberId")
        host.cellValueFactory = PropertyValueFactory("host")
        topic.cellValueFactory = PropertyValueFactory("topic")
        partition.cellValueFactory = PropertyValueFactory("partition")
        offset.cellValueFactory = PropertyValueFactory("offset")
        groupTable.items.addAll(observable)
    }

}
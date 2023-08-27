package hr.tvz.samsa.controllers

import hr.tvz.samsa.SamsaApplication
import hr.tvz.samsa.rest.RestClient
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class ClusterController {

    private val rest = RestClient()

    @Override
    fun initialize() {

        val brokerList = rest.describeCluster(SamsaApplication.selectedClusterAddress)

        clusterName.text = SamsaApplication.selectedClusterName
        clusterName.alignment = Pos.CENTER
        brokerColor.fill = Color.LIGHTGREEN
        controllerColor.fill = Color.GOLD

        var listId = mutableListOf(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11)
        var listLabel = mutableListOf(l0, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11)
        var listCircle = mutableListOf(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11)

        var i = 0

        listCircle.forEach {
            if (brokerList.size > i) {
                if (brokerList[i].isController) {
                    it.fill = Color.GOLD
                } else {
                    it.fill = Color.LIGHTGREEN
                }
            } else {
                it.isVisible = false
            }
            i++
        }
        i = 0

        listId.forEach {
            it.alignment = Pos.CENTER
            if (brokerList.size > i) {
                it.text = brokerList[i].id.toString()
            } else {
                it.isVisible = false
            }
            i++
        }
        i = 0

        listLabel.forEach {
            it.alignment = Pos.CENTER
            if (brokerList.size > i) {
                it.text = "${brokerList[i].host}:${brokerList[i].port}"
            } else {
                it.isVisible = false
            }
            i++
        }

    }

    @FXML
    private var clusterName = Label()

    @FXML
    private var brokerColor = Circle()

    @FXML
    private var controllerColor = Circle()

    @FXML
    private var i0 = Label()
    @FXML
    private var i1 = Label()
    @FXML
    private var i2 = Label()
    @FXML
    private var i3 = Label()
    @FXML
    private var i4 = Label()
    @FXML
    private var i5 = Label()
    @FXML
    private var i6 = Label()
    @FXML
    private var i7 = Label()
    @FXML
    private var i8 = Label()
    @FXML
    private var i9 = Label()
    @FXML
    private var i10 = Label()
    @FXML
    private var i11 = Label()

    @FXML
    private var l0 = Label()
    @FXML
    private var l1 = Label()
    @FXML
    private var l2 = Label()
    @FXML
    private var l3 = Label()
    @FXML
    private var l4 = Label()
    @FXML
    private var l5 = Label()
    @FXML
    private var l6 = Label()
    @FXML
    private var l7 = Label()
    @FXML
    private var l8 = Label()
    @FXML
    private var l9 = Label()
    @FXML
    private var l10 = Label()
    @FXML
    private var l11 = Label()

    @FXML
    private var c0 = Circle()
    @FXML
    private var c1 = Circle()
    @FXML
    private var c2 = Circle()
    @FXML
    private var c3 = Circle()
    @FXML
    private var c4 = Circle()
    @FXML
    private var c5 = Circle()
    @FXML
    private var c6 = Circle()
    @FXML
    private var c7 = Circle()
    @FXML
    private var c8 = Circle()
    @FXML
    private var c9 = Circle()
    @FXML
    private var c10 = Circle()
    @FXML
    private var c11 = Circle()


}
package hr.tvz.samsa.controllers

import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

class CalculatorController {

    @FXML
    private var time = Label()

    @FXML
    private var space = Label()

    @FXML
    private var timeQuantity = TextField()

    @FXML
    private var spaceQuantity = TextField()

    @FXML
    private var timeUnit = ChoiceBox<String>()

    @FXML
    private var spaceUnit = ChoiceBox<String>()

    @Override
    fun initialize() {
        populateBoxes()
        time.text = "empty"
        time.alignment = Pos.CENTER
        space.text = "empty"
        space.alignment = Pos.CENTER
    }

    @FXML
    private fun timeCalculate() {
        var initialValue = timeQuantity.text.toLong()
        when (timeUnit.value) {
            "Seconds" -> initialValue *= 1000
            "Minutes" -> initialValue *= 60000
            "Hours" -> initialValue *= 3600000
            "Days" -> initialValue *= 86400000
        }
        time.text = initialValue.toString()
    }

    @FXML
    private fun spaceCalculate() {
        var initialValue = spaceQuantity.text.toLong()
        when (spaceUnit.value) {
            "Kilobytes" -> initialValue *= 1024
            "Megabytes" -> initialValue *= 1048576
            "Gigabytes" -> initialValue *= 1073741824
        }
        space.text = initialValue.toString()
    }

    @FXML
    private fun timeCopy() {
        val clipboard = Clipboard.getSystemClipboard()
        var content = ClipboardContent()
        content.putString(time.text)
        clipboard.setContent(content)
        time.text = "Time copied to clipboard!"
    }

    @FXML
    private fun spaceCopy() {
        val clipboard = Clipboard.getSystemClipboard()
        var content = ClipboardContent()
        content.putString(space.text)
        clipboard.setContent(content)
        space.text = "Space copied to clipboard!"
    }

    private fun populateBoxes() {
        spaceUnit.items.addAll("Bytes", "Kilobytes", "Megabytes", "Gigabytes")
        timeUnit.items.addAll("Milliseconds", "Seconds", "Minutes", "Hours", "Days")
        spaceUnit.value = "Bytes"
        timeUnit.value = "Milliseconds"
    }

}
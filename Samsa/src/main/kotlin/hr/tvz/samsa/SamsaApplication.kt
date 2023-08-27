package hr.tvz.samsa

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage


class SamsaApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(SamsaApplication::class.java.getResource("selectCluster.fxml"))
        val scene = Scene(fxmlLoader.load(), 1200.0, 700.0)
        stage.title = "Samsa"
        mainStage = stage
        stage.scene = scene
        stage.show()
    }

    companion object {
        lateinit var mainStage: Stage
        lateinit var selectedClusterAddress: String
        lateinit var selectedClusterName: String
        lateinit var selectedTopic: String
        lateinit var consumeRequest: ConsumeRequest
        lateinit var refreshRate: String
        var consumeId = 0
        var consumeMap = mutableMapOf<Int, Boolean>()
        fun getStage(): Stage = mainStage
    }
}

fun main() {
    Application.launch(SamsaApplication::class.java)
}

package hr.tvz.samsa.util

import hr.tvz.samsa.ClusterConnection
import hr.tvz.samsa.Connections
import org.yaml.snakeyaml.Yaml
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class YamlParser {

    fun readConnections(file: String): List<ClusterConnection> {
        val configFile = File(file)
        val yaml = Yaml()
        val parsedData = yaml.load<Map<String, List<Map<String, String>>>>(configFile.inputStream())
        val connectionsList = parsedData["connections"]?.map { ClusterConnection(it["name"]!!, it["address"]!!) }

        return if (connectionsList != null) {
            val configData = Connections(connectionsList)
            configData.connections
        } else {
            println("No connections found in the configuration.")
            emptyList()
        }
    }

    fun writeConnection(file: String, name: String, address: String) {
        val configFile = File(file)
        val entry = "  - name: $name\n    address: $address\n"

        try {
            BufferedWriter(FileWriter(configFile, true)).use { writer ->
                writer.append(entry)
                println("New connection added to the configuration.")
            }
        } catch (e: Exception) {
            println("Error adding new connection: ${e.message}")
        }
    }

}
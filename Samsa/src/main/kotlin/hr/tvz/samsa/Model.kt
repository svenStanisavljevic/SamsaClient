package hr.tvz.samsa

data class ClusterConnection (
    val name: String,
    val address: String,
)

data class Connections (
    val connections: List<ClusterConnection>,
)

data class DescribedTopic (
    val topicName: String,
    val partitionCount: Int,
    val replicationFactor: Int,
)

data class TopicConfigUpdate(
    val configName: String,
    val configValue: String,
)

data class ProduceRequest(
    val key: String,
    val value: String,
    val ack: String,
    val compression: String,
)

data class ConsumeRequest(
    val direction: String,
    val partition: String,
)

data class ConsumedRecord (
    val offset: Long,
    val value: String,
)

data class DescribedBroker(
    val host: String,
    val port: Int,
    val id: Int,
    var isController: Boolean
)

data class DescribedGroup(
    val groupId: String,
    val state: String,
    val memberId: String,
    val host: String,
    val topic: String,
    val partition: Int,
    val offset: Long,
)
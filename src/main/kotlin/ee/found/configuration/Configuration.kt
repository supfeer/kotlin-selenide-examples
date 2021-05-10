package ee.found.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    var frontend: Frontend = Frontend()
) {
    @Serializable
    data class Frontend(
        var url: String = "localhost",
        var port: Int? = null
    )
}

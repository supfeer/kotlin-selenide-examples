package ee.found.helper

import com.charleskorn.kaml.Yaml
import ee.found.configuration.Configuration
import java.io.File
import java.nio.file.Paths

object ConfigHelper {

    fun configPath(type: String?): File {
        lateinit var configurationFile: File
        when {
            type.isNullOrEmpty()
                || type == "local" -> configurationFile = File("src/test/resources/configuration.yaml")
            else -> configurationFile = File("src/test/resources/$type-configuration.yaml")
        }

        if (!configurationFile.exists()) {
            throw NoSuchFileException(
                file = configurationFile,
                reason = "Configuration file $configurationFile " +
                    "not found in " +
                    "${Paths.get(".").toAbsolutePath().normalize()}"
            )
        }

        return configurationFile
    }

    fun getConfig() = Yaml().decodeFromString(
        deserializer = Configuration.serializer(),
        string = configPath(System.getProperty("configuration")).readText()
    )
}

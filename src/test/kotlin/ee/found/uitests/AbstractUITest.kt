package ee.found.uitests

import ee.found.helper.ConfigHelper.getConfig
import org.junit.jupiter.api.BeforeAll
import com.codeborne.selenide.Configuration as selenide

// TODO Remove this warning afterwards
@Suppress("UnnecessaryAbstractClass")
abstract class AbstractUITest {
    @BeforeAll
    fun init() {
        selenide.baseUrl = "${getConfig().frontend.url}${":" + getConfig().frontend.port}"
    }
}

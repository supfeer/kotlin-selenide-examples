package ee.found.components

import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.SelenideElement

class AlertErrorComponent <out T> {
    val component: String = "//*[@class='Toastify']"
    fun txtError(text: String): SelenideElement {
        return element(`$x`("$component//*[text()='$text']"))
    }
}

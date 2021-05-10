package ee.found.pages

import ee.found.components.AlertErrorComponent
import ee.found.components.HeaderComponent
import io.qameta.allure.Step

class MainPage : AbstractPage() {
    val header = HeaderComponent<MainPage>()
    val modalError = AlertErrorComponent<MainPage>()

    override fun endpoint(): String {
        return "/"
    }

    @Step("Login as email = {0}, password = {1}")
    fun loginAs(email: String, password: String): MainPage {
        header.fillLoginForm(email = email, password = password)
        header.btnSubmit.click()
        return this
    }
}

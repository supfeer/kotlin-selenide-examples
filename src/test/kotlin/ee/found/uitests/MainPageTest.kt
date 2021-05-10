package ee.found.uitests

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.open
import ee.found.pages.MainPage
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@Epic("Main page")
class MainPageTest : AbstractUITest() {
    val mainPage = MainPage()
    val existentUser = "qwe@qwe.qwe"
    val existentUserPassword = "password"
    val existentUserWrongPassword = "password1"
    val txtWrongEmailOrPassword = "Login failed: Incorrect email or password"

    @BeforeEach
    fun before() {
        open(mainPage.endpoint())
    }

    @Feature("Successful login of an existent user")
    @Test
    fun `Successful login an existent user`() {
        mainPage.loginAs(
            email = existentUser,
            password = existentUserPassword
        )

        mainPage.header.inputEmail.shouldBe(Condition.hidden)
    }

    @Feature("Unsuccessful login of an existent user with a wrong password")
    @Test
    fun `Unsuccessful login of an existent user with a wrong password`() {
        mainPage.loginAs(
            email = existentUser,
            password = existentUserWrongPassword
        )

        mainPage.modalError.txtError(txtWrongEmailOrPassword).shouldBe(visible)
    }
}

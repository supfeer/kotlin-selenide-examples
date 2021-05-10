package ee.found.components

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import io.kotest.matchers.shouldBe
import io.qameta.allure.Step

class HeaderComponent<out T> {
    val component = "header"
    val inputEmail = element("$component [name='email']")
    val inputPassword = element("$component #login-password")
    val btnSubmit = element("$component [type='submit']")

    @Step(
        """Fill the login form with 
        | email = {0}, 
        | password = {1}
        """
    )
    fun fillLoginForm(email: String, password: String): T {
        element(component).shouldBe(Condition.visible)
        inputEmail.click()
        inputEmail.sendKeys(email)
        inputPassword.click()
        inputPassword.sendKeys(password)
        return this as T
    }

    @Step("Submit the form")
    fun submit(): T {
        btnSubmit.click()
        return this as T
    }
}

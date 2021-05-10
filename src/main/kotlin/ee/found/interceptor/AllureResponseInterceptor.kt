package ee.found.interceptor

import com.github.kittinunf.fuel.core.FoldableResponseInterceptor
import com.github.kittinunf.fuel.core.ResponseTransformer
import ee.found.extensions.pretty
import io.qameta.allure.Allure.addAttachment

@Suppress("TooGenericExceptionCaught")
object AllureResponseInterceptor : FoldableResponseInterceptor {
    override fun invoke(next: ResponseTransformer): ResponseTransformer {
        return { request, response ->
            val url = "${request.method} ${request.url} ${response.statusCode}"
            val headers = "\n\nHeaders: \n${response.headers.pretty()}"
            val body = try {
                if (response.data.isEmpty()) ""
                else "\nBody:\n${String(response.data).pretty()}"
            } catch (e: Exception) {
                "Can't transfer body cuz: \n ${e.localizedMessage}"
            }
            addAttachment("Response", "application/json", "$url$headers$body")
            next(request, response)
        }
    }
}

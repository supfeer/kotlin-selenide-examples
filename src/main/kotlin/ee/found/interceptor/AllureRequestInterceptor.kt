package ee.found.interceptor

import com.github.kittinunf.fuel.core.FoldableRequestInterceptor
import com.github.kittinunf.fuel.core.RequestTransformer
import ee.found.extensions.pretty
import io.qameta.allure.Allure.addAttachment

object AllureRequestInterceptor : FoldableRequestInterceptor {
    override fun invoke(next: RequestTransformer): RequestTransformer {
        return { request ->
            val url = "${request.method} ${request.url}"
            val headers = "\n\nHeaders: \n${request.headers.pretty()}"
            val body = if (request.body.isEmpty()) ""
            else "\nBody:\n${String(request.body.toByteArray()).pretty()}"

            addAttachment("Request", "application/json", "$url$headers$body")

            next(request)
        }
    }
}

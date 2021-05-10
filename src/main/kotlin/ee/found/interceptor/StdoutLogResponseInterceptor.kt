package ee.found.interceptor

import com.github.kittinunf.fuel.core.FoldableResponseInterceptor
import com.github.kittinunf.fuel.core.ResponseTransformer
import ee.found.helper.lazyLogger

object StdoutLogResponseInterceptor : FoldableResponseInterceptor {
    private val logger by lazyLogger()

    override fun invoke(next: ResponseTransformer): ResponseTransformer {
        return { request, response ->
            val headers = "\nHeaders: \n${response.headers}"
            val body = if (response.data.isEmpty()) "" else "\nBody: \n${String(response.data)}"

            logger.info { " <-- ${response.statusCode} ${request.method} ${response.url}" }
            logger.debug { "$headers$body" }

            next(request, response)
        }
    }
}

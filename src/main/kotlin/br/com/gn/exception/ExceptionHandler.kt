package br.com.gn.exception

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ExceptionHandler :
    ExceptionHandler<BankException, HttpResponse<JsonError>> {

    override fun handle(
        request: HttpRequest<*>,
        exception: BankException
    ): HttpResponse<JsonError> =
        HttpResponse.status<JsonError>(exception.httpStatus)
            .body(JsonError(exception.message))

}

class ExceptionResponse {

}

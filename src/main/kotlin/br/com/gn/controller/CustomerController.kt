package br.com.gn.controller

import br.com.gn.dto.NewCustomerRequest
import br.com.gn.model.Customer
import br.com.gn.shared.manager.EntityManager
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/customers")
class CustomerController(
    @field:Inject private val manager: EntityManager
) {

    @Post
    fun persist(@Body @Valid request: NewCustomerRequest): HttpResponse<Any> {
        val customer = request.toModel()
        manager.persist(customer)
        return HttpResponse.ok(customer)
    }

    @Get
    fun findAll(): HttpResponse<Any>{
        return HttpResponse.ok(manager.find<Customer>())
    }
}
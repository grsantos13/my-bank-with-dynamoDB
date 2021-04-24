package br.com.gn.controller

import br.com.gn.dto.CustomerRequest
import br.com.gn.dto.CustomerResponse
import br.com.gn.exception.BankException
import br.com.gn.model.Customer
import br.com.gn.shared.manager.EntityManager
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.NOT_FOUND
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/customers")
class CustomerController(
    @field:Inject private val manager: EntityManager
) {

    @Post
    fun persist(@Body @Valid request: CustomerRequest): HttpResponse<Any> {
        val customer = request.toModel()
        manager.save(customer)
        return HttpResponse.ok(CustomerResponse(customer))
    }

    @Get
    fun findAll(): HttpResponse<Any> {
        val customers = manager.findByPkAndSk<Customer>(Customer.PREFIX, Customer.PREFIX)
            .map(::CustomerResponse)
        return HttpResponse.ok(customers)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<Any> {
        val customer = manager.findById<Customer>(Customer.PREFIX, Customer.withPrefix(id))
            ?: throw BankException(NOT_FOUND, "Customer not found with id $id")
        return HttpResponse.ok(CustomerResponse(customer))
    }

    @Put("/{id}")
    fun update(@PathVariable id: String, @Body @Valid request: CustomerRequest): HttpResponse<Any> {
        val customer = manager.findById<Customer>(Customer.PREFIX, Customer.withPrefix(id))
            ?: throw BankException(NOT_FOUND, "Customer not found with id $id")

        customer.update(request)
        manager.save(customer)
        return HttpResponse.ok(CustomerResponse(customer))
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: String): HttpResponse<Any> {
        manager.delete<Customer>(Customer.PREFIX, Customer.withPrefix(id))
        return HttpResponse.ok()
    }
}
package br.com.gn.dto

import br.com.gn.model.Address
import br.com.gn.model.Customer
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class CustomerRequest(
    @field:NotBlank var name: String?,
    @field:NotBlank var phone: String?,
    @field:NotBlank @field:Email var email: String?,
    @field:NotNull var address: Address?
) {
    fun toModel(): Customer {
        return Customer(name!!, phone!!, email!!, address!!)
    }
}

class CustomerResponse(customer: Customer) {
    val id = customer.sk.split("#")[1]
    val name = customer.name
    val email = customer.email
    val phone = customer.phone
    val address = customer.address
}

class SimpleCustomerResponse(customer: Customer) {
    val name = customer.name
    val email = customer.email
}
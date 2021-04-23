package br.com.gn.dto

import br.com.gn.model.Customer
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Introspected
data class NewCustomerRequest(
    @field:NotBlank var name: String?,
    @field:NotBlank var phone: String?,
    @field:NotBlank @field:Email var email: String?
) {
    fun toModel(): Customer {
        return Customer(name, phone, email)
    }
}

class CustomerResponse(customer: Customer) {
    val id = customer.sk.split("#")[1]
    val name = customer.name
    val email = customer.email
    val phone = customer.phone
}
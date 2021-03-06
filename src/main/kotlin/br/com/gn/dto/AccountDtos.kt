package br.com.gn.dto

import br.com.gn.exception.BankException
import br.com.gn.model.Account
import br.com.gn.model.AccountType
import br.com.gn.model.Customer
import br.com.gn.shared.manager.EntityManager
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class AccountRequest(
    @field:NotBlank var number: String,
    @field:NotBlank var agency: String,
    @field:NotNull var type: AccountType
) {
    fun toModel(manager: EntityManager, customerId: String): Account {
        val customerIdWithPrefix = Customer.withPrefix(customerId)

        manager.findById<Customer>(Customer.PREFIX, customerIdWithPrefix)
            ?: throw BankException(HttpStatus.NOT_FOUND, "Customer does not exist with id $customerId")

        return Account(
            number = number,
            agency = agency,
            customerId = customerIdWithPrefix,
            type = type
        )
    }
}

class AccountResponse(
    val id: String,
    val number: String,
    val agency: String,
    val customer: SimpleCustomerResponse,
    val type: AccountType
) {
    companion object {
        fun from(account: Account, manager: EntityManager): AccountResponse {
            val customer = manager.findById<Customer>(Customer.PREFIX, account.customerId)
                ?: throw BankException(HttpStatus.NOT_FOUND, "Customer does not exist with id ${account.customerId}")

            return with(account) {
                AccountResponse(
                    id = sk.split("#")[1],
                    number = number,
                    agency = agency,
                    customer = SimpleCustomerResponse(customer),
                    type = type
                )
            }
        }
    }
}


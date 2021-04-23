package br.com.gn.controller

import br.com.gn.dto.AccountRequest
import br.com.gn.dto.AccountResponse
import br.com.gn.model.Account
import br.com.gn.model.Customer
import br.com.gn.shared.manager.EntityManager
import com.amazonaws.services.kms.model.NotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/customers/{customerId}/accounts")
class AccountController(
    @field:Inject private val manager: EntityManager
) {
    @Post
    fun persist(@PathVariable customerId: String, @Body @Valid request: AccountRequest): HttpResponse<Any> {
        val account = request.toModel(manager, customerId)
        manager.save(account)
        return HttpResponse.ok(AccountResponse.from(account, manager))
    }

    @Get
    fun findAll(@PathVariable customerId: String): HttpResponse<Any> {
        val accounts = manager.findByPkAndSk<Account>("CTMR#$customerId", "ACT#")
            .map { AccountResponse.from(it, manager) }
        return HttpResponse.ok(accounts)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String, @PathVariable customerId: String): HttpResponse<Any> {
        val account = manager.findById<Account>("CTMR#$customerId", "ACT#$id")
            ?: throw IllegalArgumentException("Not found")
        return HttpResponse.ok(AccountResponse.from(account, manager))
    }

    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @PathVariable customerId: String,
        @Body @Valid request: AccountRequest
    ): HttpResponse<Any> {
        manager.findById<Customer>("CUSTOMER#", "CTMR#$customerId")
            ?: throw NotFoundException("Customer not found with id $customerId")

        val account = manager.findById<Account>("CTMR#${customerId}", "ACT#$id")
            ?: throw IllegalArgumentException("Not found")


        account.update(request)
        manager.save(account)
        return HttpResponse.ok(AccountResponse.from(account, manager))
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: String): HttpResponse<Any> {
        manager.delete<Account>("CUSTOMER#", "CTMR#$id")
        return HttpResponse.ok()
    }
}
package br.com.gn.exception

import io.micronaut.http.HttpStatus

data class BankException(
    val httpStatus: HttpStatus,
    val msg: String
) : RuntimeException(msg)
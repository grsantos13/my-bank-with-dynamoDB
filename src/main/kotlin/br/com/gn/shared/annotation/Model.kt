package br.com.gn.shared.annotation

import io.micronaut.core.annotation.Introspected

@NoArg
@Introspected
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Model()

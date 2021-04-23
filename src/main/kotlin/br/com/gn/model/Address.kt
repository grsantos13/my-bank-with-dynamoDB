package br.com.gn.model

import br.com.gn.shared.annotation.Model
import javax.validation.constraints.NotBlank

@Model
data class Address(
    @field:NotBlank val street: String?,
    @field:NotBlank val number: String?,
    @field:NotBlank val district: String?,
    @field:NotBlank val city: String?,
    @field:NotBlank val state: String?,
    @field:NotBlank val country: String?,
    @field:NotBlank val complement: String?,
)

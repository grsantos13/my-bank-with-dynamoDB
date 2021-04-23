package br.com.gn.config.dynamo

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/structure")
class GeneralController(
    @field:Inject private val structure: Structure
) {

    @Post("/generate-table/{name}")
    fun generateTable(@PathVariable name: String) {
        structure.regenerateTable(name)
    }
}
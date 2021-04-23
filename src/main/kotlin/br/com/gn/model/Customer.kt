package br.com.gn.model

import br.com.gn.dto.CustomerRequest
import br.com.gn.shared.annotation.Model
import com.amazonaws.services.dynamodbv2.datamodeling.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Model
@DynamoDBTable(tableName = "bank")
class Customer(

    @field:NotBlank
    @DynamoDBAttribute(attributeName = "name")
    var name: String,

    @field:NotBlank
    @DynamoDBAttribute(attributeName = "phone")
    var phone: String,

    @field:NotBlank
    @field:Email
    var email: String,

    @field:Valid
    @field:NotNull
    @DynamoDBAttribute(attributeName = "address")
    @DynamoDBTypeConvertedJson
    var address: Address
) {
    fun update(request: CustomerRequest) {
        name = request.name!!
        phone = request.phone!!
        email = request.email!!
    }

    @DynamoDBHashKey(attributeName = "pk")
    var id: String = "CUSTOMER#"

    @DynamoDBRangeKey(attributeName = "sk")
    var sk: String = "CTMR#${UUID.randomUUID()}"

}

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

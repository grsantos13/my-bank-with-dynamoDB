package br.com.gn.model

import br.com.gn.dto.AccountRequest
import br.com.gn.shared.annotation.Model
import com.amazonaws.services.dynamodbv2.datamodeling.*
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Model
@DynamoDBTable(tableName = "bank")
class Account(
    @field:NotBlank
    @DynamoDBAttribute(attributeName = "number")
    var number: String,

    @field:NotBlank
    @DynamoDBAttribute(attributeName = "agency")
    var agency: String,

    @field:NotBlank
    @DynamoDBHashKey(attributeName = "pk")
    var customerId: String,

    @field:NotNull
    @DynamoDBAttribute(attributeName = "account_type")
    @DynamoDBTypeConvertedEnum
    var type: AccountType
) {

    @DynamoDBRangeKey(attributeName = "sk")
    var sk: String = "ACT#${UUID.randomUUID()}"

    fun update(request: AccountRequest) {
        number = request.number
        agency = request.agency
        type = request.type
    }
}
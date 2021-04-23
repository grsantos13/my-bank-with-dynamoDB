package br.com.gn.model

import br.com.gn.shared.annotation.Model
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Model
@DynamoDBTable(tableName = "bank")
class Customer(

    @field:NotBlank
    @DynamoDBAttribute(attributeName = "name")
    var name: String?,

    @field:NotBlank
    @DynamoDBAttribute(attributeName = "phone")
    var phone: String?,

    @field:NotBlank
    @field:Email
    @DynamoDBRangeKey(attributeName = "sk")
    var email: String?
) {

    @DynamoDBHashKey(attributeName = "pk")
    var id: String = "#CUSTOMER#${UUID.randomUUID()}"

}

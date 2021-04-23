package br.com.gn.config.dynamo

import br.com.gn.model.Customer
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Structure(
    @field:Inject private val client: DynamoDB,
    @field:Inject private val mapperClient: AmazonDynamoDB
) {

    private val mapper = DynamoDBMapper(mapperClient)

    companion object {
        private const val TABLE_NAME = "bank"
        private const val PK_NAME = "pk"
        private const val SK_NAME = "sk"
    }

    fun regenerateTable(name: String = TABLE_NAME) {
        if (tableExists(name)) {
            client.getTable(name).delete()
        }
        if (tableExists("customer")) {
            client.getTable("customer").delete()
        }
        createTable(name)
    }

    private fun tableExists(name: String = TABLE_NAME): Boolean {
        return client.listTables().any { it.tableName == name }
    }

    private fun createTable(name: String = TABLE_NAME) {
        val request = CreateTableRequest()
            .withTableName(name)
            .withAttributeDefinitions(AttributeDefinition(PK_NAME, ScalarAttributeType.S))
            .withAttributeDefinitions(AttributeDefinition(SK_NAME, ScalarAttributeType.S))
            .withKeySchema(
                listOf(
                    KeySchemaElement(PK_NAME, KeyType.HASH),
                    KeySchemaElement(SK_NAME, KeyType.RANGE),
                )
            )
            .withBillingMode(BillingMode.PAY_PER_REQUEST)

        client.createTable(request).waitForActive()
    }

    fun insert(customer: Customer): Customer {
        mapper.save(customer)
        return customer
    }

    fun find(): List<Customer>? {
        val scanExpression = DynamoDBScanExpression()
        return mapper.scan(Customer::class.java, scanExpression)
    }
}
package br.com.gn.shared.manager

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntityManager(
    @field:Inject val mapperClient: AmazonDynamoDB
) {

    fun getMapper() = DynamoDBMapper(mapperClient)

    inline fun <reified T> persist(entity: T): T {
        getMapper().save(entity)
        return entity
    }

    inline fun <reified T> find(): List<T> {
        return getMapper().scan(T::class.java, DynamoDBScanExpression())
    }
}
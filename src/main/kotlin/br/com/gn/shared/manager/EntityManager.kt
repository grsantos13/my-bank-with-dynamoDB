package br.com.gn.shared.manager

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntityManager(
    @field:Inject val mapperClient: AmazonDynamoDB
) {

    fun getMapper() = DynamoDBMapper(mapperClient)

    inline fun <reified T> save(entity: T): T {
        getMapper().save(entity)
        return entity
    }

    inline fun <reified T> findById(pk: String, sk: String): T? {
        return getMapper().load(T::class.java, pk, sk)
    }

    inline fun <reified T> findByPkAndSk(pk: String, sk: String): List<T> {
        val query = DynamoDBQueryExpression<T>()
            .withKeyConditionExpression("pk = :v1 and begins_with(sk, :v2)")
            .withExpressionAttributeValues(
                mapOf(
                    ":v1" to AttributeValue().withS(pk),
                    ":v2" to AttributeValue().withS(sk)
                )
            )

        return getMapper()
            .query(T::class.java, query)
    }

    inline fun <reified T> delete(pk: String, sk: String) {
        val entity = findById<T>(pk, sk)
        getMapper().delete(entity)
    }

}
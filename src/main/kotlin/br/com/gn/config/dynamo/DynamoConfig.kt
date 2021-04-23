package br.com.gn.config.dynamo

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory

@Factory
class DynamoConfig {

    @Bean
    fun dynamoDbClient(): DynamoDB {
        val accessKey = "guaw2"
        val secretKey = "hdytqh"
        val endpoint = "http://localhost:4566"

        val credentials = BasicAWSCredentials(accessKey, secretKey)
        val clientBuilder = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, "us-west-2"))
            .build()
        return DynamoDB(clientBuilder)
    }

    @Bean
    fun dynamoDbClient2(): AmazonDynamoDB {
        val accessKey = "guaw2"
        val secretKey = "hdytqh"
        val endpoint = "http://localhost:4566"

        val credentials = BasicAWSCredentials(accessKey, secretKey)
        val clientBuilder = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, "us-west-2"))
        return clientBuilder.build()
    }
}
package org.whispersystems.textsecuregcm.util;

import java.net.URI;
import org.whispersystems.textsecuregcm.configuration.DynamoDbClientConfiguration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbFromConfig {

  public static DynamoDbClient client(DynamoDbClientConfiguration config, AwsCredentialsProvider credentialsProvider) {
    return DynamoDbClient.builder()
        .region(Region.US_WEST_2)
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create("accesskey", "secretkey")))
        .endpointOverride(URI.create("http://localhost:8000")) // Local DynamoDB endpoint
        .overrideConfiguration(ClientOverrideConfiguration.builder()
            .apiCallTimeout(config.clientExecutionTimeout())
            .apiCallAttemptTimeout(config.clientRequestTimeout())
            .build())
        .httpClientBuilder(ApacheHttpClient.builder()
            .maxConnections(config.maxConnections()))
        .build();

//    return DynamoDbClient.builder()
//        .region(Region.of(config.region()))
//        .credentialsProvider(credentialsProvider)
//        .overrideConfiguration(ClientOverrideConfiguration.builder()
//            .apiCallTimeout(config.clientExecutionTimeout())
//            .apiCallAttemptTimeout(config.clientRequestTimeout())
//            .build())
//        .httpClientBuilder(ApacheHttpClient.builder()
//            .maxConnections(config.maxConnections()))
//        .build();
  }

  public static DynamoDbAsyncClient asyncClient(
      DynamoDbClientConfiguration config,
      AwsCredentialsProvider credentialsProvider) {
    return DynamoDbAsyncClient.builder()
        .region(Region.US_WEST_2) // Change to your desired region
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create("accesskey", "secretkey")))
        .endpointOverride(URI.create("http://localhost:8000"))
        .overrideConfiguration(ClientOverrideConfiguration.builder()
            .apiCallTimeout(config.clientExecutionTimeout())
            .apiCallAttemptTimeout(config.clientRequestTimeout())
            .build())
        .httpClientBuilder(NettyNioAsyncHttpClient.builder()
            .maxConcurrency(config.maxConnections()))
        .build();

//    return DynamoDbAsyncClient.builder()
//        .region(Region.of(config.region()))
//        .credentialsProvider(credentialsProvider)
//        .overrideConfiguration(ClientOverrideConfiguration.builder()
//            .apiCallTimeout(config.clientExecutionTimeout())
//            .apiCallAttemptTimeout(config.clientRequestTimeout())
//            .build())
//        .httpClientBuilder(NettyNioAsyncHttpClient.builder()
//            .maxConcurrency(config.maxConnections()))
//        .build();
  }
}

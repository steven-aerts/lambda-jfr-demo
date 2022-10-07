package com.airties.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;


public class Lambda implements RequestHandler<APIGatewayV2HTTPEvent, List<String>> {
    private S3AsyncClient s3Client = S3AsyncClient.builder()
            .region(Region.EU_CENTRAL_1)
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .build();

    @Override
    public List<String> handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        return s3Client.listBuckets().join().buckets().stream().map(b -> b.name()).collect(Collectors.toList());
    }
}
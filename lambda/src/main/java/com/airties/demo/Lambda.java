package com.airties.demo;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

import software.amazon.awssdk.services.s3.S3Client;


public class Lambda implements RequestHandler<APIGatewayV2HTTPEvent, List<String>> {
    @Override
    public List<String> handleRequest(APIGatewayV2HTTPEvent request, Context context) {        
        return S3Client.create().listBuckets().buckets().stream().map(b -> b.name()).collect(Collectors.toList());
    }
}
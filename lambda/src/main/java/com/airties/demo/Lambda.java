package com.airties.demo;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

import software.amazon.awssdk.services.s3.S3Client;


public class Lambda implements RequestHandler<APIGatewayV2HTTPEvent, List<String>> {
    private S3Client s3Client = S3Client.create();

    @Override
    public List<String> handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        if (request.getQueryStringParameters() != null && request.getQueryStringParameters().containsKey("jfr")) {
            JFRDumper.run(context);
        }
        return s3Client.listBuckets().buckets().stream().map(b -> b.name()).collect(Collectors.toList());
    }
}
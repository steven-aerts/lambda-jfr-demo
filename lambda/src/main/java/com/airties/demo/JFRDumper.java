package com.airties.demo;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import com.amazonaws.services.lambda.runtime.Context;

import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import software.amazon.awssdk.services.s3.S3AsyncClient;

/**
 * Helper class dumping JFR traces to S3.
 * Dump location is extracted from System.getenv("JFR_DUMP_PATH") and should be from the format s3://bucket/path/
 * It expects JFR to be running.
 * This can for example be done by setting environment JAVA_TOOL_OPTIONS="-XX:StartFlightRecording=maxsize=50M,settings=profile"
 **/
public class JFRDumper {
    private static S3AsyncClient s3Client;

    public static void run(Context context) {
        FlightRecorder flightRecorder = FlightRecorder.getFlightRecorder();
        if (flightRecorder.getRecordings().isEmpty()) {
            return;
        }
        try (Recording recording = flightRecorder.getRecordings().get(0).copy(false)) {
            Path file = Files.createTempFile("JFR_recording", ".jfr");
            recording.dump(file);
            URI uri = URI.create(System.getenv("JFR_DUMP_PATH"));
            if(!"s3".equals(uri.getScheme())) {
                throw new IllegalArgumentException("Unable to understand JFR_RECORDING_PATH " + uri);
            }
            String bucket = uri.getHost();
            String key = uri.getPath().substring(1) + context.getLogStreamName() + "/" + file.getFileName();
            if (s3Client == null) {
                s3Client = S3AsyncClient.create();
            }
            s3Client.putObject(b -> b.bucket(bucket).key(key), file).join();
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

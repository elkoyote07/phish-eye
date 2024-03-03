package com.jesus.phisheye.operator;

import com.jesus.phisheye.config.BasicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

@Service
public class DnsInfoOperatorImpl implements DnsInfoOperator{

    @Autowired
    private BasicConfig basicConfig;
    @Override
    public void getInfo(String dns) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://whoisjsonapi.com/v1/" + dns))
                .header("Authorization", "Bearer " + basicConfig.getWhoisApiKey())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
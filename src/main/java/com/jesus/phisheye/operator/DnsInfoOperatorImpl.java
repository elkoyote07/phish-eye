package com.jesus.phisheye.operator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus.phisheye.config.BasicConfig;
import com.jesus.phisheye.dto.DomainDTO;
import com.jesus.phisheye.dto.RootDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DnsInfoOperatorImpl implements DnsInfoOperator{

    @Autowired
    private BasicConfig basicConfig;
    @Override
    public RootDTO getInfo(String dns) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://whoisjsonapi.com/v1/" + dns))
                .header("Authorization", "Bearer " + basicConfig.getWhoisApiKey())
                .build();
        RootDTO rootDTO;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Status Code: " + response.statusCode());
            //System.out.println("Response Body: " + response.body());
            rootDTO = objectMapper.readValue(response.body(), RootDTO.class);
            //System.out.println(rootDTO);
            return rootDTO;
        } catch (IOException | InterruptedException e) {
            rootDTO = new RootDTO();
            DomainDTO domainDTO = new DomainDTO();
            domainDTO.setDomain(dns);
            rootDTO.setDomain(domainDTO);
            return rootDTO;
        }
    }
}

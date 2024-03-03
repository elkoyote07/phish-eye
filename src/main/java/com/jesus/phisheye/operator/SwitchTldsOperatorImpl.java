package com.jesus.phisheye.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class SwitchTldsOperatorImpl implements SwitchTldsOperator {
    @Override
    public Set<String> genByDnsWithoutTld(String originDns) {
        Set<String> dnsSet = new HashSet<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tlds-iana.txt");

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    dnsSet.add((originDns + "." + line).toLowerCase());
                }
            } catch (IOException e) {
                log.error("Error reading file", e);
            }
        } else {
            log.error("File not found");
        }
        return dnsSet;
    }
}

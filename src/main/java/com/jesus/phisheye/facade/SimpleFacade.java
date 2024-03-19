package com.jesus.phisheye.facade;

import java.util.Set;

public interface SimpleFacade {
    Set<String> genHomoglyphByDnsWithoutTld(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore);

    Set<String> genSwitchTldsByDnsWithoutTld(String originDns);

    void printInfo(String originDns);

    void automatedFullProcessByDns(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore, String tls);
}

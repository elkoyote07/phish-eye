package com.jesus.phisheye.facade;

import com.jesus.phisheye.dto.RootDTO;

import java.util.Set;

public interface SimpleFacade {
    Set<String> genHomoglyphByDnsWithoutTld(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore);

    Set<String> genSwitchTldsByDnsWithoutTld(String originDns);

    RootDTO printInfo(String originDns);

    void automatedFullProcessByDns(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore, String tls);

    Set<String> phisheyeProcess(String fullDns, String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore, String tls);
}

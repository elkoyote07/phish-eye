package com.jesus.phisheye.operator;

import java.util.Set;

public interface HomoglyphOperator {
    Set<String> genByDnsWithoutTld(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore);
}

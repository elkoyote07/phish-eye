package com.jesus.phisheye.operator;

import java.util.Set;

public interface SwitchTldsOperator {
    Set<String> genByDnsWithoutTld(String originDns);
}

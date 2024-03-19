package com.jesus.phisheye.operator;

import com.jesus.phisheye.dto.RootDTO;

public interface DnsInfoOperator {
    RootDTO getInfo(String dns);
}

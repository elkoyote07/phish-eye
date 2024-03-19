package com.jesus.phisheye.operator;

import com.jesus.phisheye.dto.RootDTO;

import java.util.Set;

public interface SaverOperator {
    void fullRootSet(Set<RootDTO> rootDTOSet);

    void clean(String originDns);

    Set<RootDTO> findFullDns(String fullDns);
}

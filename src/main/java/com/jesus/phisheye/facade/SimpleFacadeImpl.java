package com.jesus.phisheye.facade;

import com.jesus.phisheye.operator.DnsInfoOperator;
import com.jesus.phisheye.operator.HomoglyphOperator;
import com.jesus.phisheye.operator.SwitchTldsOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class SimpleFacadeImpl implements SimpleFacade{

    @Autowired
    private HomoglyphOperator homoglyphOperator;

    @Autowired
    private SwitchTldsOperator switchTldsOperator;

    @Autowired
    private DnsInfoOperator dnsInfoOperator;

    @Override
    public Set<String> genHomoglyphByDnsWithoutTld(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore) {
        log.info("Starting simple facade homoglyphs based on dns");
        Set<String> homoglyphsResultList = homoglyphOperator.genByDnsWithoutTld(originDns, applyVisualSimilarity, visualSimilarityScore);
        log.info("Finishing simple facade homoglyphs based on dns");
        return homoglyphsResultList;
    }

    @Override
    public Set<String> genSwitchTldsByDnsWithoutTld(String originDns) {
        log.info("Starting simple facade typposquatting switching tlds");
        Set<String> switchTldsResultList = switchTldsOperator.genByDnsWithoutTld(originDns);
        log.info("Finishing simple facade typposquatting switching tlds");
        return switchTldsResultList;
    }

    @Override
    public void printInfo(String originDns) {
        log.info("Starting simple facade print info dns");
        dnsInfoOperator.getInfo(originDns);
        log.info("Finishing simple facade print info dns");
    }
}

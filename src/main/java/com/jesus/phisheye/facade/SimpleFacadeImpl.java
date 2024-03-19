package com.jesus.phisheye.facade;

import com.jesus.phisheye.dto.RootDTO;
import com.jesus.phisheye.operator.DnsInfoOperator;
import com.jesus.phisheye.operator.HomoglyphOperator;
import com.jesus.phisheye.operator.SaverOperator;
import com.jesus.phisheye.operator.SwitchTldsOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    @Autowired
    private SaverOperator saverOperator;

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
    public RootDTO printInfo(String originDns) {
        log.info("Starting simple facade print info dns");
        RootDTO rootDTO = dnsInfoOperator.getInfo(originDns);
        log.info("Finishing simple facade print info dns");
        return rootDTO;
    }

    @Override
    public void automatedFullProcessByDns(String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore, String tls) {
        log.info("Starting fully process");

        log.info("Starting simple facade homoglyphs based on dns");
        Set<String> homoglyphsResultList = homoglyphOperator.genByDnsWithoutTld(originDns, applyVisualSimilarity, visualSimilarityScore);
        homoglyphsResultList.add(originDns);
        log.info("Finishing simple facade homoglyphs based on dns");

        Set<String> switchTldsResultList = new HashSet<>();
        for (String dnsHomoglyph:
                homoglyphsResultList){
            log.info("Starting simple facade typposquatting switching tlds");
            Set<String> tmp = new HashSet<>();
            tmp = switchTldsOperator.genByDnsWithoutTld(dnsHomoglyph);
            switchTldsResultList.addAll(tmp);
            log.info("Finishing simple facade typposquatting switching tlds");
        }

        Set<RootDTO> rootDTOSet = new HashSet<>();
        for (String dns:
        switchTldsResultList){
            log.info("Starting simple facade print info dns");
            RootDTO rootDTO = dnsInfoOperator.getInfo(dns);

            if (rootDTO.getDomain().getDomain().contains(originDns + tls)){
                rootDTO.setOwned(true);
            } else {
                rootDTO.setOwned(false);
            }

            if (rootDTO.getDomain().getId() != null){
                rootDTO.setIsInfo(true);
            } else {
                rootDTO.setIsInfo(false);
            }

            rootDTO.setDns(rootDTO.getDomain().getDomain());

            rootDTO.setOriginDns(originDns + tls);

            rootDTOSet.add(rootDTO);
            log.info("Finishing simple facade print info dns");
        }
        log.info("Starting saver process");
        saverOperator.fullRootSet(rootDTOSet);
        log.info("Finished saver process");

        log.info("Finished fully process");
    }

    @Override
    public Set<String> phisheyeProcess(String fullDns, String originDns, Boolean applyVisualSimilarity, int visualSimilarityScore, String tls) {
        log.info("Starting fully process");

        Set<RootDTO> initRootDTOSet = saverOperator.findFullDns(fullDns);

        log.info("Starting simple facade homoglyphs based on dns");
        Set<String> homoglyphsResultList = homoglyphOperator.genByDnsWithoutTld(originDns, applyVisualSimilarity, visualSimilarityScore);
        homoglyphsResultList.add(originDns);
        log.info("Finishing simple facade homoglyphs based on dns");

        Set<String> switchTldsResultList = new HashSet<>();
        for (String dnsHomoglyph:
                homoglyphsResultList){
            log.info("Starting simple facade typposquatting switching tlds");
            Set<String> tmp = new HashSet<>();
            tmp = switchTldsOperator.genByDnsWithoutTld(dnsHomoglyph);
            switchTldsResultList.addAll(tmp);
            log.info("Finishing simple facade typposquatting switching tlds");
        }

        Set<RootDTO> rootDTOSet = new HashSet<>();
        for (String dns:
                switchTldsResultList){
            log.info("Starting simple facade print info dns");
            RootDTO rootDTO = dnsInfoOperator.getInfo(dns);

            if (rootDTO.getDomain().getDomain().contains(fullDns)){
                rootDTO.setOwned(true);
            } else {
                rootDTO.setOwned(false);
            }

            if (rootDTO.getDomain().getId() != null){
                rootDTO.setIsInfo(true);
            } else {
                rootDTO.setIsInfo(false);
            }

            rootDTO.setDns(rootDTO.getDomain().getDomain());

            rootDTO.setOriginDns(originDns + tls);

            rootDTOSet.add(rootDTO);
            log.info("Finishing simple facade print info dns");
        }

        saverOperator.clean(fullDns);
        log.info("Starting saver process");
        saverOperator.fullRootSet(rootDTOSet);
        log.info("Finished saver process");

        Set<String> foundAlert = new HashSet<>();
        for (RootDTO newRootDTO : rootDTOSet) {
            for (RootDTO initRootDTO : initRootDTOSet) {
                if (newRootDTO.getDns().equals(initRootDTO.getDns())) {
                    if (!initRootDTO.getIsInfo() && newRootDTO.getIsInfo()) {
                        foundAlert.add(newRootDTO.getDns());
                    }
                    break;
                }
            }
        }
        log.info("Finished fully process");
        return foundAlert;
    }


}

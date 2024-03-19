package com.jesus.phisheye.controller;

import com.jesus.phisheye.facade.SimpleFacade;
import com.jesus.phisheye.operator.HomoglyphOperator;
import com.jesus.phisheye.operator.SwitchTldsOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Document Controller", description = "Endpoints for Document")
@Slf4j
@RestController
@RequestMapping("/dns")
public class DomainNameServerController {

    @Autowired
    private SimpleFacade simpleFacade;

    @Operation(summary = "Create homoglyphs based on dns")
    @PostMapping(path = "/homoglyph")
    public ResponseEntity<Set<String>> createHomoglyphs(
            @RequestParam(name = "original", required = true) String originDns,
            @RequestParam(name = "visual_similarity", required = true, defaultValue = "false") Boolean applyVisualSimilarity,
            @RequestParam(name = "visual_similarity_score", required = true) int visualSimilarityScore
    ){
        log.info("Starting endpoint create homoglyphs based on dns");
        Set<String> homoglyphsResultList = simpleFacade.genHomoglyphByDnsWithoutTld(originDns, applyVisualSimilarity, visualSimilarityScore);
        log.info("Finishing endpoint create homoglyphs based on dns");
        return new ResponseEntity<>(homoglyphsResultList, HttpStatus.OK);
    }


    @Operation(summary = "Create typposquatting switching tlds")
    @PostMapping(path = "/switchtlds")
    public ResponseEntity<Set<String>> createTyppoTldSwitch(
            @RequestParam(name = "original", required = true) String originDns
    ){
        log.info("Starting endpoint create typposquatting switching tlds");
        Set<String> switchTldsResultList = simpleFacade.genSwitchTldsByDnsWithoutTld(originDns);
        log.info("Finishing endpoint create typposquatting switching tlds");
        return new ResponseEntity<>(switchTldsResultList, HttpStatus.OK);
    }

    @Operation(summary = "Print info dns")
    @GetMapping(path = "/info")
    public ResponseEntity<?> get(
            @RequestParam(name = "original", required = true) String originDns
    ){
        log.info("Starting endpoint print info dns");
        simpleFacade.printInfo(originDns);
        log.info("Finishing endpoint print info dns");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "All process init")
    @PostMapping(path = "/full-init")
    public ResponseEntity<Set<String>> fullInit(
            @RequestParam(name = "original", required = true) String originDns,
            @RequestParam(name = "visual_similarity", required = true, defaultValue = "false") Boolean applyVisualSimilarity,
            @RequestParam(name = "visual_similarity_score", required = true) int visualSimilarityScore,
            @RequestParam(name = "tls", required = true) String tls
    ){
        log.info("Starting all process init");
        simpleFacade.automatedFullProcessByDns(originDns, applyVisualSimilarity, visualSimilarityScore, tls);
        log.info("Finished all process init");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

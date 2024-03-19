package com.jesus.phisheye.operator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus.phisheye.dto.DomainDTO;
import com.jesus.phisheye.dto.RootDTO;
import com.jesus.phisheye.entity.*;
import com.jesus.phisheye.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaverOperatorImpl implements SaverOperator{

    @Autowired
    private RootRepository rootRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private RegistrarRepository registrarRepository;

    @Autowired
    private RegistrantRepository registrantRepository;

    @Autowired
    private NameServersRepository nameServersRepository;

    @Autowired
    private NMDomainNServersRepository nmDomainNServersRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private NMDomainStatusRepository nmDomainStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public void fullRootSet(Set<RootDTO> rootDTOSet) {

        Set<RootEntity> rootEntitySet = new HashSet<>();

        for (RootDTO rootDTO:
        rootDTOSet){

            RootEntity rootEntity = modelMapper.map(rootDTO, RootEntity.class);
            rootEntity.setDomainEntity(domainRepository.save(getDomainEntity(rootDTO)));
            if (rootEntity.getDomainEntity().getName() == null){
                rootEntitySet.add(rootEntity);
                continue;
            }
            if (rootDTO.getRegistrant() != null){
                rootEntity.setRegistrantEntity(registrantRepository.save(modelMapper.map(rootDTO.getRegistrant(), RegistrantEntity.class)));

            }
            if (rootDTO.getRegistrar() != null){
                rootEntity.setRegistrarEntity(registrarRepository.save(modelMapper.map(rootDTO.getRegistrar(), RegistrarEntity.class)));

            }

            List<NameServersEntity> nameServersEntityList = new ArrayList<>();
            List<NMDomainNServersEntity> nmDomainNServersEntityList = new ArrayList<>();
            for (String nameServer :
            rootDTO.getDomain().getNameServerslist()){
                NameServersEntity nameServersEntity = new NameServersEntity();
                nameServersEntity.setName(nameServer);
                nameServersEntityList.add(nameServersEntity);
                NMDomainNServersEntity nmDomainNServersEntity = new NMDomainNServersEntity();
                nmDomainNServersEntity.setDomainEntity(rootEntity.getDomainEntity());
                nmDomainNServersEntity.setNameServersEntity(nameServersRepository.save(nameServersEntity));
                nmDomainNServersEntityList.add(nmDomainNServersEntity);
            }
            nmDomainNServersRepository.saveAll(nmDomainNServersEntityList);


            List<StatusEntity> statusEntityList = new ArrayList<>();
            List<NMDomainStatusEntity> nmDomainStatusEntityList = new ArrayList<>();
            for (String status:
            rootDTO.getDomain().getStatusList()){
                StatusEntity statusEntity = new StatusEntity();
                statusEntity.setName(status);
                statusEntityList.add(statusEntity);
                NMDomainStatusEntity nmDomainStatusEntity = new NMDomainStatusEntity();
                nmDomainStatusEntity.setDomainEntity(rootEntity.getDomainEntity());
                nmDomainStatusEntity.setStatusEntity(statusRepository.save(statusEntity));
                nmDomainStatusEntityList.add(nmDomainStatusEntity);
            }
            nmDomainStatusRepository.saveAll(nmDomainStatusEntityList);

            rootEntitySet.add(rootEntity);

        }

        rootRepository.saveAll(rootEntitySet);

    }

    private DomainEntity getDomainEntity(RootDTO rootDTO) {
        DomainEntity domainEntity = new DomainEntity();
        DomainDTO domainDTO = rootDTO.getDomain();
        domainEntity.setExternalId(domainDTO.getId());
        domainEntity.setName(domainDTO.getName());
        domainEntity.setCreatedDate(domainDTO.getCreatedDate());
        domainEntity.setCreatedDateInTime(domainDTO.getCreatedDateInTime());
        domainEntity.setDomainName(domainDTO.getDomain());
        domainEntity.setExtension(domainDTO.getExtension());
        domainEntity.setExpirationDate(domainDTO.getExpirationDate());
        domainEntity.setExpirationDateInTime(domainDTO.getExpirationDateInTime());
        domainEntity.setPunycode(domainDTO.getPunycode());
        domainEntity.setUpdatedDate(domainDTO.getUpdatedDate());
        domainEntity.setUpdatedDateInTime(domainDTO.getUpdatedDateInTime());
        domainEntity.setWhoisServer(domainDTO.getWhoisServer());
        return domainEntity;
    }
}

package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.TelevisionInputDTO;
import com.novi.techiteasy.DTO.TelevisionOutputDTO;
import com.novi.techiteasy.models.Television;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.repositories.TelevisionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelevisionService {

    @Autowired
    private TelevisionRepository televisionRepository;

//    public TelevisionOutputDTO transferModelToOutputDTO(Television television) {
//        TelevisionOutputDTO televisionDTO = new TelevisionOutputDTO();
//        BeanUtils.copyProperties(televisionDTO, television);
//        return televisionDTO;
//    }

    public Television transferOutputDTOToModel(TelevisionOutputDTO televisionOutputDTO) {
        Television television = new Television();
        BeanUtils.copyProperties(television, televisionOutputDTO);
        return television;
    }

//    public TelevisionInputDTO transferModelToInputDTO(Television television) {
//        TelevisionInputDTO televisionInputDTO = new TelevisionInputDTO();
//        System.out.println(television.getName());
//        BeanUtils.copyProperties(television, televisionInputDTO);
//        System.out.println(televisionInputDTO.name);
//        System.out.println(television.getName());
//        return televisionInputDTO;
//    }

    public Television transferInputDTOToModel(TelevisionInputDTO televisionInputDTO) {
        System.out.println(televisionInputDTO.name);
        Television television = new Television();
        //BeanUtils.copyProperties(televisionInputDTO, television, "id");
        television.setId(televisionInputDTO.id);
        television.setType(televisionInputDTO.type);
        television.setBrand(televisionInputDTO.brand);
        television.setName(televisionInputDTO.name);
        television.setPrice(televisionInputDTO.price);
        television.setAvailableSize(televisionInputDTO.availableSize);
        television.setRefreshRate(televisionInputDTO.refreshRate);
        television.setScreenType(televisionInputDTO.screenType);
        television.setScreenQuality(televisionInputDTO.screenQuality);
        television.setSmartTv(televisionInputDTO.smartTv);
        television.setWifi(televisionInputDTO.wifi);
        television.setVoiceControl(televisionInputDTO.voiceControl);
        television.setHdr(televisionInputDTO.hdr);
        television.setBluetooth(televisionInputDTO.bluetooth);
        television.setAmbiLight(televisionInputDTO.ambiLight);
        television.setOriginalStock(televisionInputDTO.originalStock);
        television.setSold(televisionInputDTO.sold);
        System.out.println(television.getName());
        return television;
    }

    public List<Television> getAllTelevisions() {
        List<Television> televisions = televisionRepository.findAll();
        if (televisions.isEmpty()) { throw new RecordNotFoundException("There are currently no televisions in the database"); }

        televisions.addAll(televisions);

        return televisions;
    }

    public Television getTelevisionById(Long id) {
        Television television = televisionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Television not found with id: " + id));;
        return television;
    }

    public Television createTelevision(TelevisionInputDTO televisionInputDTO) {
        Television createdTelevision = televisionRepository.save(transferInputDTOToModel(televisionInputDTO));
        return createdTelevision;
    }

    public List<Television> createTelevisions(List<TelevisionInputDTO> televisionsInputDTOList) {
        List<Television> createdTelevisions = new ArrayList<>();
        for (TelevisionInputDTO t : televisionsInputDTOList) {
            Television createdTelevision = televisionRepository.save(transferInputDTOToModel(t));
            createdTelevisions.add(createdTelevision);
        }
        return createdTelevisions;
    }

    public Television updateTelevision(Long id, TelevisionInputDTO updatedTelevisionInputDTO) {
        Television existingTelevision = getTelevisionById(id);
        BeanUtils.copyProperties(updatedTelevisionInputDTO, existingTelevision, "id");
        Television updatedTelevision = televisionRepository.save(existingTelevision);

        return updatedTelevision;
    }

    public void deleteTelevision(Long id) {
        Television television = getTelevisionById(id);
        if (television == null) throw new RecordNotFoundException("Television not found with ID: " + id);
        televisionRepository.delete(television);
    }
}

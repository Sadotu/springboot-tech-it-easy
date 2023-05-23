package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.Input.CiModuleInputDTO;
import com.novi.techiteasy.DTO.Output.CiModuleOutputDTO;
import com.novi.techiteasy.DTO.Output.TelevisionOutputDTO;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.models.CiModule;
import com.novi.techiteasy.models.Remote;
import com.novi.techiteasy.models.Television;
import com.novi.techiteasy.repositories.CiModuleRepository;
import com.novi.techiteasy.repositories.TelevisionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CiModuleService {

    private final CiModuleRepository ciModuleRepository;
    private final TelevisionRepository televisionRepository;

    public CiModuleService(CiModuleRepository ciModuleRepository, TelevisionRepository televisionRepository) {
        this.ciModuleRepository = ciModuleRepository;
        this.televisionRepository = televisionRepository;
    }

    public CiModuleOutputDTO transferModelToOutputDTO(CiModule ciModule) {
        CiModuleOutputDTO ciModuleOutputDTO = new CiModuleOutputDTO();
        BeanUtils.copyProperties(ciModule, ciModuleOutputDTO);
        return ciModuleOutputDTO;
    }

    public CiModule transferInputDTOToModel(CiModuleInputDTO ciModuleInputDTO) {
        CiModule ciModule = new CiModule();
        BeanUtils.copyProperties(ciModuleInputDTO, ciModule, "id");
        return ciModule;
    }

    public CiModuleOutputDTO createCiModule(CiModuleInputDTO ciModuleInputDTO) {
        CiModule createdCiModule = ciModuleRepository.save(transferInputDTOToModel(ciModuleInputDTO));
        return transferModelToOutputDTO(createdCiModule);
    }

    public List<CiModuleOutputDTO> createCiModules(List<CiModuleInputDTO> ciModulesInputDTOList) {
        List<CiModule> createdCiModules = new ArrayList<>();
        for (CiModuleInputDTO t : ciModulesInputDTOList) {
            CiModule createdCiModule = ciModuleRepository.save(transferInputDTOToModel(t));
            createdCiModules.add(createdCiModule);
        }
        List<CiModuleOutputDTO> createdCiModulesOutputDTO = new ArrayList<>();
        for (CiModule t : createdCiModules) {
            CiModuleOutputDTO ciModuleOutputDTO = transferModelToOutputDTO(t);
            createdCiModulesOutputDTO.add(ciModuleOutputDTO);
        }
        return createdCiModulesOutputDTO;
    }

    public CiModule getCiModuleById(Long id) {
        return ciModuleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("CiModule not found with id: " + id));
    }

    public List<CiModule> getAllCiModules() {
        List<CiModule> ciModules = ciModuleRepository.findAll();
        if (ciModules.isEmpty()) { throw new RecordNotFoundException("There are currently no ciModules in the database"); }

        ciModules.addAll(ciModules);

        return ciModules;
    }

    public CiModuleOutputDTO updateCiModule(Long id, CiModuleInputDTO updatedCiModuleInputDTO) {
        CiModule existingCiModule = getCiModuleById(id);
        BeanUtils.copyProperties(updatedCiModuleInputDTO, existingCiModule, "id");
        CiModule updatedCiModule = ciModuleRepository.save(existingCiModule);

        return transferModelToOutputDTO(updatedCiModule);
    }

    public String assignTelevisionToCiModule(Long id, Long television_id) throws RecordNotFoundException {
        Optional<CiModule> optionalCiModule = ciModuleRepository.findById(id);
        Optional<Television> optionalTelevision = televisionRepository.findById(television_id);
        if(optionalCiModule.isEmpty() && optionalCiModule.isEmpty()) {
            throw new RecordNotFoundException("Television or CiModule with" + television_id + " and " + id + "does not exist");
        }
        CiModule ciModule = optionalCiModule.get();
        Television television = optionalTelevision.get();
        ciModule.setTelevision(television);
        ciModuleRepository.save(ciModule);
        return "Hoera!";
    }

    public void deleteCiModule(Long id) {
        CiModule ciModule = getCiModuleById(id);
        if (ciModule == null) throw new RecordNotFoundException("CiModule not found with ID: " + id);
        ciModuleRepository.delete(ciModule);
    }
}


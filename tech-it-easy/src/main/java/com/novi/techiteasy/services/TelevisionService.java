package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.Input.TelevisionInputDTO;
import com.novi.techiteasy.DTO.Output.TelevisionOutputDTO;
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

    public TelevisionOutputDTO transferModelToOutputDTO(Television television) {
        TelevisionOutputDTO televisionOutputDTO = new TelevisionOutputDTO();
        BeanUtils.copyProperties(television, televisionOutputDTO);
        return televisionOutputDTO;
    }

    public Television transferInputDTOToModel(TelevisionInputDTO televisionInputDTO) {
        Television television = new Television();
        BeanUtils.copyProperties(televisionInputDTO, television, "id");
        return television;
    }

    public TelevisionOutputDTO createTelevision(TelevisionInputDTO televisionInputDTO) {
        Television createdTelevision = televisionRepository.save(transferInputDTOToModel(televisionInputDTO));
        return transferModelToOutputDTO(createdTelevision);
    }

    public List<TelevisionOutputDTO> createTelevisions(List<TelevisionInputDTO> televisionsInputDTOList) {
        List<Television> createdTelevisions = new ArrayList<>();
        for (TelevisionInputDTO t : televisionsInputDTOList) {
            Television createdTelevision = televisionRepository.save(transferInputDTOToModel(t));
            createdTelevisions.add(createdTelevision);
        }
        List<TelevisionOutputDTO> createdTelevisionsOutputDTO = new ArrayList<>();
        for (Television t : createdTelevisions) {
            TelevisionOutputDTO televisionOutputDTO = transferModelToOutputDTO(t);
            createdTelevisionsOutputDTO.add(televisionOutputDTO);
        }
        return createdTelevisionsOutputDTO;
    }

    public Television getTelevisionById(Long id) {
        Television television = televisionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Television not found with id: " + id));;
        return television;
    }

    public List<Television> getAllTelevisions() {
        List<Television> televisions = televisionRepository.findAll();
        if (televisions.isEmpty()) { throw new RecordNotFoundException("There are currently no televisions in the database"); }

        televisions.addAll(televisions);

        return televisions;
    }

    public TelevisionOutputDTO updateTelevision(Long id, TelevisionInputDTO updatedTelevisionInputDTO) {
        Television existingTelevision = getTelevisionById(id);
        BeanUtils.copyProperties(updatedTelevisionInputDTO, existingTelevision, "id");
        Television updatedTelevision = televisionRepository.save(existingTelevision);

        return transferModelToOutputDTO(updatedTelevision);
    }

    public void deleteTelevision(Long id) {
        Television television = getTelevisionById(id);
        if (television == null) throw new RecordNotFoundException("Television not found with ID: " + id);
        televisionRepository.delete(television);
    }
}

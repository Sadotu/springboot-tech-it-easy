package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.Input.TelevisionInputDTO;
import com.novi.techiteasy.DTO.Output.TelevisionOutputDTO;
import com.novi.techiteasy.models.Remote;
import com.novi.techiteasy.models.Television;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.models.WallBracket;
import com.novi.techiteasy.repositories.RemoteRepository;
import com.novi.techiteasy.repositories.TelevisionRepository;
import com.novi.techiteasy.repositories.WallBracketRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {

    private final TelevisionRepository televisionRepository;
    private final RemoteRepository remoteRepository;
    private final WallBracketRepository wallBracketRepository;

    public TelevisionService(TelevisionRepository televisionRepository, RemoteRepository remoteRepository, WallBracketRepository wallBracketRepository) {
        this.televisionRepository = televisionRepository;
        this.remoteRepository = remoteRepository;
        this.wallBracketRepository = wallBracketRepository;
    }

    public TelevisionOutputDTO transferModelToOutputDTO(Television television) {
        TelevisionOutputDTO televisionOutputDTO = new TelevisionOutputDTO();
        BeanUtils.copyProperties(television, televisionOutputDTO);
        return televisionOutputDTO;
    }

    public Television transferInputDTOToModel(TelevisionInputDTO televisionInputDTO) {
        Television television = new Television();
        BeanUtils.copyProperties(televisionInputDTO, television);
        return television;
    }

    public TelevisionOutputDTO createTelevision(TelevisionInputDTO televisionInputDTO) {
        return transferModelToOutputDTO(televisionRepository.save(transferInputDTOToModel(televisionInputDTO)));
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

    public TelevisionOutputDTO updateTelevision(Long id, TelevisionInputDTO updatedTelevisionInputDTO) throws RecordNotFoundException {
        Television existingTelevision = getTelevisionById(id);
        BeanUtils.copyProperties(updatedTelevisionInputDTO, existingTelevision, "id");
        Television updatedTelevision = televisionRepository.save(existingTelevision);

        return transferModelToOutputDTO(updatedTelevision);
    }

    public TelevisionOutputDTO assignRemoteToTelevision(Long id, Long remote_id) throws RecordNotFoundException {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        Optional<Remote> optionalRemote = remoteRepository.findById(remote_id);
        if(optionalTelevision.isEmpty() && optionalRemote.isEmpty()) {
            throw new RecordNotFoundException("Remote or television with" + remote_id + " and " + id + "does not exist");
        }
        Television television = optionalTelevision.get();
        Remote remote = optionalRemote.get();
        television.setRemote(remote);
        Television updateTelevision =  televisionRepository.save(television);
        return transferModelToOutputDTO(updateTelevision);
    }

    public String assignWallbracketToTelevision(Long id, Long wallbracket_id) throws RecordNotFoundException {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        Optional<WallBracket> optionalWallBracket = wallBracketRepository.findById(wallbracket_id);
        if(optionalTelevision.isEmpty() && optionalWallBracket.isEmpty()) {
            throw new RecordNotFoundException("Wallbracket or television with" + wallbracket_id + " and " + id + "does not exist");
        }
        Television television = optionalTelevision.get();
        WallBracket wallBracket = optionalWallBracket.get();
        List<WallBracket> wallBracketList = television.getWallBrackets();
        wallBracketList.add(wallBracket);
        television.setWallBrackets(wallBracketList);
        televisionRepository.save(television);
        return "hoera!";
    }

    public void deleteTelevision(Long id) {
        Television television = getTelevisionById(id);
        if (television == null) throw new RecordNotFoundException("Television not found with ID: " + id);
        televisionRepository.delete(television);
    }
}

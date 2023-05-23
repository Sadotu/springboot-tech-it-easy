package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.Input.WallBracketInputDTO;
import com.novi.techiteasy.DTO.Output.WallBracketOutputDTO;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.models.WallBracket;
import com.novi.techiteasy.repositories.WallBracketRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WallBracketService {

    @Autowired
    private WallBracketRepository wallBracketRepository;

    public WallBracketOutputDTO transferModelToOutputDTO(WallBracket wallBracket) {
        WallBracketOutputDTO wallBracketOutputDTO = new WallBracketOutputDTO();
        BeanUtils.copyProperties(wallBracket, wallBracketOutputDTO);
        return wallBracketOutputDTO;
    }

    public WallBracket transferInputDTOToModel(WallBracketInputDTO wallBracketInputDTO) {
        WallBracket wallBracket = new WallBracket();
        BeanUtils.copyProperties(wallBracketInputDTO, wallBracket, "id");
        return wallBracket;
    }

    public WallBracketOutputDTO createWallBracket(WallBracketInputDTO wallBracketInputDTO) {
        WallBracket createdWallBracket = wallBracketRepository.save(transferInputDTOToModel(wallBracketInputDTO));
        return transferModelToOutputDTO(createdWallBracket);
    }

    public List<WallBracketOutputDTO> createWallBrackets(List<WallBracketInputDTO> wallBracketsInputDTOList) {
        List<WallBracket> createdWallBrackets = new ArrayList<>();
        for (WallBracketInputDTO t : wallBracketsInputDTOList) {
            WallBracket createdWallBracket = wallBracketRepository.save(transferInputDTOToModel(t));
            createdWallBrackets.add(createdWallBracket);
        }
        List<WallBracketOutputDTO> createdWallBracketsOutputDTO = new ArrayList<>();
        for (WallBracket t : createdWallBrackets) {
            WallBracketOutputDTO wallBracketOutputDTO = transferModelToOutputDTO(t);
            createdWallBracketsOutputDTO.add(wallBracketOutputDTO);
        }
        return createdWallBracketsOutputDTO;
    }

    public WallBracket getWallBracketById(Long id) {
        return wallBracketRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("WallBracket not found with id: " + id));
    }

    public List<WallBracket> getAllWallBrackets() {
        List<WallBracket> wallBrackets = wallBracketRepository.findAll();
        if (wallBrackets.isEmpty()) { throw new RecordNotFoundException("There are currently no wallBrackets in the database"); }

        wallBrackets.addAll(wallBrackets);

        return wallBrackets;
    }

    public WallBracketOutputDTO updateWallBracket(Long id, WallBracketInputDTO updatedWallBracketInputDTO) {
        WallBracket existingWallBracket = getWallBracketById(id);
        BeanUtils.copyProperties(updatedWallBracketInputDTO, existingWallBracket, "id");
        WallBracket updatedWallBracket = wallBracketRepository.save(existingWallBracket);

        return transferModelToOutputDTO(updatedWallBracket);
    }

    public void deleteWallBracket(Long id) {
        WallBracket wallBracket = getWallBracketById(id);
        if (wallBracket == null) throw new RecordNotFoundException("WallBracket not found with ID: " + id);
        wallBracketRepository.delete(wallBracket);
    }
}

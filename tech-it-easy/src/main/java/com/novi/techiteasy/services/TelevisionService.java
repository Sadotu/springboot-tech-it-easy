package com.novi.techiteasy.services;

import com.novi.techiteasy.Television;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.repositories.TelevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {

    @Autowired
    private TelevisionRepository televisionRepository;

    public List<Television> getAllTelevisions() {
        return televisionRepository.findAll();
    }

    public Television getTelevisionById(Long id) {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        return optionalTelevision.orElseThrow(() -> new RecordNotFoundException("Television not found with id: " + id));
    }

    public Television addTelevision(Television television) {
        return televisionRepository.save(television);
    }

    public List<Television> createTelevisions(List<Television> televisions) {
        List<Television> createdTelevisions = new ArrayList<>();
        for (Television television : televisions) {
            Television createdTelevision = televisionRepository.save(television);
            createdTelevisions.add(createdTelevision);
        }
        return createdTelevisions;
    }

    public Television updateTelevision(Long id, Television updatedTelevision) {
        Television existingTelevision = getTelevisionById(id);
        existingTelevision.setBrand(updatedTelevision.getBrand());
        existingTelevision.setModel(updatedTelevision.getModel());
        existingTelevision.setType(updatedTelevision.getType());
        return televisionRepository.save(existingTelevision);
    }

    public void deleteTelevision(Long id) {
        Television television = getTelevisionById(id);
        televisionRepository.delete(television);
    }
}

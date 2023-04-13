package com.novi.techiteasy.controller;

import com.novi.techiteasy.Television;
import com.novi.techiteasy.services.TelevisionService;
import com.novi.techiteasy.exceptions.RecordNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    @Autowired
    private TelevisionService televisionService;

    @GetMapping
    public List<Television> getAllTelevisions() {
        return televisionService.getAllTelevisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Television> getTelevisionById(@PathVariable Long id) {
        Television television = televisionService.getTelevisionById(id);
        if (television == null) {
            throw new RecordNotFoundException("Television not found with id: " + id);
        }
        return ResponseEntity.ok(television);
    }

    @PostMapping
    public ResponseEntity<List<Television>> createTelevisions(@RequestBody List<Television> televisions) {
        List<Television> createdTelevisions = televisionService.createTelevisions(televisions);
        return new ResponseEntity<>(createdTelevisions, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(@PathVariable Long id, @RequestBody Television television) {
        Television updatedTelevision = televisionService.updateTelevision(id, television);
        if (updatedTelevision == null) {
            throw new RecordNotFoundException("Television not found with id: " + id);
        }
        return ResponseEntity.ok(updatedTelevision);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable Long id) {
        televisionService.deleteTelevision(id);
        return ResponseEntity.noContent().build();
    }

}

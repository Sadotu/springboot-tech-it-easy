package com.novi.techiteasy.controller;

import com.novi.techiteasy.DTO.TelevisionInputDTO;
import com.novi.techiteasy.DTO.TelevisionOutputDTO;
import com.novi.techiteasy.models.Television;
import com.novi.techiteasy.services.TelevisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        return ResponseEntity.ok(televisionService.getTelevisionById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Television> createTelevision(
            @Validated @RequestBody TelevisionInputDTO televisionInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.status(HttpStatus.CREATED).body(televisionService.createTelevision(televisionInputDTO));
    }

    @PostMapping("/addmore")
    public ResponseEntity<List<Television>> createTelevisions(
            @Validated @RequestBody List<TelevisionInputDTO> televisionsInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return new ResponseEntity<>(televisionService.createTelevisions(televisionsInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(
            @Validated @PathVariable Long id, @RequestBody TelevisionInputDTO televisionInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.ok(televisionService.updateTelevision(id, televisionInputDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable Long id) {
        televisionService.deleteTelevision(id);
        return ResponseEntity.noContent().build();
    }

}

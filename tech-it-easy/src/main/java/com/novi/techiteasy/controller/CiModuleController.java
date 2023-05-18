package com.novi.techiteasy.controller;

import com.novi.techiteasy.DTO.Input.CiModuleInputDTO;
import com.novi.techiteasy.DTO.Output.CiModuleOutputDTO;
import com.novi.techiteasy.models.CiModule;
import com.novi.techiteasy.services.CiModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cimodules")
public class CiModuleController {

    @Autowired
    private CiModuleService ciModuleService;

    @PostMapping("/add/cimodule")
    public ResponseEntity<CiModuleOutputDTO> createCiModule(
            @Validated @RequestBody CiModuleInputDTO ciModuleInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.status(HttpStatus.CREATED).body(ciModuleService.createCiModule(ciModuleInputDTO));
    }

    @PostMapping("/addmore/cimodules")
    public ResponseEntity<List<CiModuleOutputDTO>> createCiModules(
            @Validated @RequestBody List<CiModuleInputDTO> ciModulesInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return new ResponseEntity<>(ciModuleService.createCiModules(ciModulesInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiModule> getCiModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(ciModuleService.getCiModuleById(id));
    }

    @GetMapping
    public List<CiModule> getAllCiModules() {
        return ciModuleService.getAllCiModules();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CiModuleOutputDTO> updateCiModule(
            @Validated @PathVariable Long id, @RequestBody CiModuleInputDTO ciModuleInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.ok(ciModuleService.updateCiModule(id, ciModuleInputDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCiModule(@PathVariable Long id) {
        ciModuleService.deleteCiModule(id);
        return ResponseEntity.noContent().build();
    }
}

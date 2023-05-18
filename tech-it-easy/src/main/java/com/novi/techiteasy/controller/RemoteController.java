package com.novi.techiteasy.controller;

import com.novi.techiteasy.DTO.Input.RemoteInputDTO;
import com.novi.techiteasy.DTO.Input.TelevisionInputDTO;
import com.novi.techiteasy.DTO.Output.RemoteOutputDTO;
import com.novi.techiteasy.DTO.Output.TelevisionOutputDTO;
import com.novi.techiteasy.models.Remote;
import com.novi.techiteasy.models.Television;
import com.novi.techiteasy.services.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remotes")
public class RemoteController {

    @Autowired
    private RemoteService remoteService;

    @PostMapping("/add/remote")
    public ResponseEntity<RemoteOutputDTO> createRemote(
            @Validated @RequestBody RemoteInputDTO remoteInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.status(HttpStatus.CREATED).body(remoteService.createRemote(remoteInputDTO));
    }

    @PostMapping("/addmore/remotes")
    public ResponseEntity<List<RemoteOutputDTO>> createRemotes(
            @Validated @RequestBody List<RemoteInputDTO> remotesInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return new ResponseEntity<>(remoteService.createRemotes(remotesInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Remote> getRemoteById(@PathVariable Long id) {
        return ResponseEntity.ok(remoteService.getRemoteById(id));
    }

    @GetMapping
    public List<Remote> getAllRemotes() {
        return remoteService.getAllRemotes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemoteOutputDTO> updateRemote(
            @Validated @PathVariable Long id, @RequestBody RemoteInputDTO remoteInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().build(); }
        return ResponseEntity.ok(remoteService.updateRemote(id, remoteInputDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemote(@PathVariable Long id) {
        remoteService.deleteRemote(id);
        return ResponseEntity.noContent().build();
    }
}

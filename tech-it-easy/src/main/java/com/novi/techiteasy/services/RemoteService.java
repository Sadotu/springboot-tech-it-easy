package com.novi.techiteasy.services;

import com.novi.techiteasy.DTO.Input.RemoteInputDTO;
import com.novi.techiteasy.DTO.Output.RemoteOutputDTO;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.models.Remote;
import com.novi.techiteasy.repositories.RemoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteService {

    @Autowired
    private RemoteRepository remoteRepository;

    public RemoteOutputDTO transferModelToOutputDTO(Remote remote) {
        RemoteOutputDTO remoteOutputDTO = new RemoteOutputDTO();
        BeanUtils.copyProperties(remote, remoteOutputDTO);
        return remoteOutputDTO;
    }

    public Remote transferInputDTOToModel(RemoteInputDTO remoteInputDTO) {
        Remote remote = new Remote();
        BeanUtils.copyProperties(remoteInputDTO, remote, "id");
        return remote;
    }

    public RemoteOutputDTO createRemote(RemoteInputDTO remoteInputDTO) {
        Remote createdRemote = remoteRepository.save(transferInputDTOToModel(remoteInputDTO));
        return transferModelToOutputDTO(createdRemote);
    }

    public List<RemoteOutputDTO> createRemotes(List<RemoteInputDTO> remotesInputDTOList) {
        List<Remote> createdRemotes = new ArrayList<>();
        for (RemoteInputDTO t : remotesInputDTOList) {
            Remote createdRemote = remoteRepository.save(transferInputDTOToModel(t));
            createdRemotes.add(createdRemote);
        }
        List<RemoteOutputDTO> createdRemotesOutputDTO = new ArrayList<>();
        for (Remote t : createdRemotes) {
            RemoteOutputDTO remoteOutputDTO = transferModelToOutputDTO(t);
            createdRemotesOutputDTO.add(remoteOutputDTO);
        }
        return createdRemotesOutputDTO;
    }

    public Remote getRemoteById(Long id) {
        return remoteRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Remote not found with id: " + id));
    }

    public List<Remote> getAllRemotes() {
        List<Remote> remotes = remoteRepository.findAll();
        if (remotes.isEmpty()) { throw new RecordNotFoundException("There are currently no remotes in the database"); }

        remotes.addAll(remotes);

        return remotes;
    }

    public RemoteOutputDTO updateRemote(Long id, RemoteInputDTO updatedRemoteInputDTO) {
        Remote existingRemote = getRemoteById(id);
        BeanUtils.copyProperties(updatedRemoteInputDTO, existingRemote, "id");
        Remote updatedRemote = remoteRepository.save(existingRemote);

        return transferModelToOutputDTO(updatedRemote);
    }

    public void deleteRemote(Long id) {
        Remote remote = getRemoteById(id);
        if (remote == null) throw new RecordNotFoundException("Remote not found with ID: " + id);
        remoteRepository.delete(remote);
    }
}

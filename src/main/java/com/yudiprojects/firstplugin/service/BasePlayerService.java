package com.yudiprojects.firstplugin.service;

import com.yudiprojects.firstplugin.dto.PlayerSignUp;
import com.yudiprojects.firstplugin.repository.PlayerRepository;
import com.yudiprojects.firstplugin.model.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasePlayerService implements PlayerService {

    private final PlayerRepository repository;

    @Override
    public void createPlayer(PlayerSignUp playerSignUp) {


        PlayerEntity player = PlayerEntity.builder()
                .id(playerSignUp.getId())
                .username(playerSignUp.getUsername())
                .hashPassword(playerSignUp.getPassword())
                .build();

        repository.save(player);
    }

    @Override
    public void deletePlayerById(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public Optional<PlayerEntity> getPlayerById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<PlayerEntity> getPlayerByNick(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<PlayerEntity> getPlayers() {
        return repository.findAll();
    }
}

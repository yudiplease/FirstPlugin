package com.yudiprojects.firstplugin.service;

import com.yudiprojects.firstplugin.dto.PlayerSignUp;
import com.yudiprojects.firstplugin.model.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerService {
    void createPlayer(PlayerSignUp playerSignUp);

    void deletePlayerById(UUID uuid);

    Optional<PlayerEntity> getPlayerById(UUID id);

    Optional<PlayerEntity> getPlayerByNick(String name);
    List<PlayerEntity> getPlayers();
}

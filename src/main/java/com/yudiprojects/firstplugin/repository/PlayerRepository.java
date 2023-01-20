package com.yudiprojects.firstplugin.repository;


import com.yudiprojects.firstplugin.model.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    List<PlayerEntity> findAll();

    void save(PlayerEntity entity);

    Optional<PlayerEntity> findById(UUID id);

    Optional<PlayerEntity> findByName(String name);

    void deleteById(UUID id);

}

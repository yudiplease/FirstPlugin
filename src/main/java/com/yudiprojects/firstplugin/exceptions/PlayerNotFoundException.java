package com.yudiprojects.firstplugin.exceptions;

import java.util.UUID;

public class PlayerNotFoundException extends NotFoundException {
    public PlayerNotFoundException(String name) {
        super(String.format("Player with this name = %s, not found", name));
    }

    public PlayerNotFoundException(UUID id) {
        super(String.format("Player with this id = %s, not found", id));
    }
}

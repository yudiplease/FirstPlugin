package com.yudiprojects.firstplugin.model;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {

    private UUID id;
    private String username;
    private String hashPassword;
}

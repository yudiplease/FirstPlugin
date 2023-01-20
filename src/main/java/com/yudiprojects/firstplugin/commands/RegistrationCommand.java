package com.yudiprojects.firstplugin.commands;

import com.yudiprojects.firstplugin.config.properties.SimpleDataSource;
import com.yudiprojects.firstplugin.dto.PlayerSignUp;
import com.yudiprojects.firstplugin.listeners.EventListener;
import com.yudiprojects.firstplugin.repository.BasePlayerRepository;
import com.yudiprojects.firstplugin.repository.PlayerRepository;
import com.yudiprojects.firstplugin.service.BasePlayerService;
import com.yudiprojects.firstplugin.service.PlayerService;
import com.yudiprojects.firstplugin.util.HashCreator;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegistrationCommand implements CommandExecutor {

    HashCreator hashCreator = new HashCreator();

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(EventListener.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        DataSource dataSource = new SimpleDataSource(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );

        PlayerRepository repository = new BasePlayerRepository(dataSource);
        PlayerService playerService = new BasePlayerService(repository);

        String password = Stream.of(args)
                .collect(Collectors.joining(""));


        PlayerSignUp playerSignUp = PlayerSignUp.builder()
                .id(UUID.randomUUID())
                .username(sender.getName())
                .password(hashCreator.createSHAHash(password))
                .build();

        playerService.createPlayer(playerSignUp);

        return true;
    }
}

package com.yudiprojects.firstplugin.listeners;

import com.yudiprojects.firstplugin.config.properties.SimpleDataSource;
import com.yudiprojects.firstplugin.model.PlayerEntity;
import com.yudiprojects.firstplugin.repository.BasePlayerRepository;
import com.yudiprojects.firstplugin.repository.PlayerRepository;
import com.yudiprojects.firstplugin.service.BasePlayerService;
import com.yudiprojects.firstplugin.service.PlayerService;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class EventListener implements Listener  {

    public EventListener() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
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
        PlayerService service = new BasePlayerService(repository);

        String playerName = event.getPlayer().getName();

        Player player = event.getPlayer();

        if (service.getPlayerByNick(playerName).isPresent()) {
            player.sendMessage("Hi!");
        } else {
            player.sendMessage("You aren't registered! /reg");
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0);
            player.setDisplayName("[UnRegistered] " + playerName);
        }
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onPlayerChatting(AsyncPlayerChatEvent event) {
        event.setFormat(ChatColor.GREEN + event.getPlayer().getDisplayName() + ": " + event.getMessage());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandSendEvent event) {

    }

    public void freezePlayer(PlayerMoveEvent e) {
        e.setTo(e.getFrom());
        ;
    }
}

package com.yudiprojects.firstplugin;

import com.yudiprojects.firstplugin.commands.RegistrationCommand;
import com.yudiprojects.firstplugin.listeners.EventListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class FirstPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Plugin has been started.");

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.getCommand("reg").setExecutor(new RegistrationCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Plugin has been stopped");
    }
}

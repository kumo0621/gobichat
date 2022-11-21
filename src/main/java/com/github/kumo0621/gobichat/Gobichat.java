package com.github.kumo0621.gobichat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Gobichat extends JavaPlugin implements org.bukkit.event.Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    FileConfiguration config;


    @EventHandler
    public void onPlayerchat(AsyncPlayerChatEvent e) {

        String setmessage = config.getString(e.getPlayer().getName());
        if (setmessage != null) {
            e.setFormat("<%1$s> " + "%2$s" + setmessage);


        }
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals("gobi")) {
            if (sender instanceof Player) {
                if (args.length <= 1) {
                    sender.sendMessage("/gobi 名前 語尾");
                } else {
                    config.set(args[0], args[1]);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.YELLOW + args[0] + ChatColor.WHITE + "さんの名前を「" + ChatColor.GREEN + args[1] + ChatColor.WHITE + "」にしました。");
                    saveConfig();
                }
            }
        }
        return super.onCommand(sender, command, label, args);
    }
}

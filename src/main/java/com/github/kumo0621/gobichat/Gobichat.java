package com.github.kumo0621.gobichat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

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
    String setmessage;

    Collection<? extends Player> player = Bukkit.getOnlinePlayers();
    boolean start = false;

    @EventHandler
    public void onPlayerchat(AsyncPlayerChatEvent e) {
        if (start) {
            setmessage = config.getString(e.getPlayer().getName());
            if (setmessage != null) {
                e.setFormat("<%1$s> " + "%2$s" + setmessage);
            }

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
        if (command.getName().equals("random")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    sender.sendMessage("/random @r");
                } else {
                    for (int i = 0; i < args.length; i++) {
                        String arg = args[i];

                        if (arg.startsWith("@")) {
                            try {
                                List<Entity> selected = getServer().selectEntities(sender, arg);

                                if (selected.size() == 1) {
                                    Entity entity = selected.iterator().next();
                                    args[i] = (entity instanceof Player) ? entity.getName() : entity.getUniqueId().toString();
                                    String count = String.valueOf(RandomCount.random());
                                    String get = config.getString(count);
                                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.YELLOW + args[i] + ChatColor.WHITE + "さんの語尾をランダムに変更しました");
                                    config.set(args[i], get);
                                    saveConfig();
                                }
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                    }
                }
            }
        }
        if (command.getName().equals("allrandom")) {
            if (sender instanceof Player) {
                for (Player nameList : player) {
                    String count2 = String.valueOf(RandomCount.random());
                    String get2 = config.getString(count2);
                    config.set(nameList.getName(), get2);
                    saveConfig();
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.GREEN + "プレイヤー全員" + ChatColor.WHITE + "の語尾を変更しました。");

            }
        }
        if (command.getName().equals("cancel")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    sender.sendMessage("/cancel all or 名前");
                } else {
                    if(args[0].equals("all")) {
                            for (Player allPlayer : player) {
                                config.set(allPlayer.getName(), " ");
                                saveConfig();
                            }
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.YELLOW + "全員" + ChatColor.WHITE + "の名前を" + ChatColor.GREEN + "リセット" + ChatColor.WHITE + "しました。");
                    } else {
                        config.set(args[0], " ");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.YELLOW + args[0] + ChatColor.WHITE + "さんの名前を" + ChatColor.GREEN + "リセット" + ChatColor.WHITE + "しました。");
                        saveConfig();
                    }
                    }
                }
            }

        if (command.getName().equals("allgobi")) {
            if (sender instanceof Player) {
                for (Player allPlayer : player) {
                    config.set(allPlayer.getName(), args[0]);
                    saveConfig();
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + ChatColor.YELLOW + "全員" + ChatColor.WHITE + "の名前を" + ChatColor.GREEN + args[0] + ChatColor.WHITE + "しました。");
            }
        }
        if (command.getName().equals("gobiset")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    sender.sendMessage("/gobiset on or off");
                } else {
                    switch (args[0]) {
                        case "on":
                            start = true;
                            break;
                        case "off":
                            start = false;
                            break;
                    }
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[くもぱわ～] " + "語尾を開始しました");
            }
        }
        return super.onCommand(sender, command, label, args);
    }

}
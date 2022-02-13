package me.black.b_discordintegration;

import me.black.b_discordintegration.Discord.Discord;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class B_DiscordIntegration extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getLogger().info("Plugin§a enabled!");

        //REGISTER EVENT
        getServer().getPluginManager().registerEvents(this, this);

        //ENABLE INFO
        Discord webhook = new Discord(getConfig().getString("webhookURL"));
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(java.awt.Color.GREEN)
                .setDescription(getConfig().getString("serverenable")));
        try {
            webhook.execute();
        } catch (java.io.IOException e) {
            getLogger().severe(e.getStackTrace().toString());
        }

        //CONFIG
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

        reloadConfig();

        getLogger().info("Plugin§c disabled!");

        //DISABLE INFO
        Discord webhook = new Discord(getConfig().getString("webhookURL"));
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(java.awt.Color.RED)
                .setDescription(getConfig().getString("serverdisable")));
        try {
            webhook.execute();
        } catch (java.io.IOException e) {
            getLogger().severe(e.getStackTrace().toString());
        }
    }

    @EventHandler
    //CHAT INTEGRATION
    public void DCczat(AsyncPlayerChatEvent e) {
        Discord webhook = new Discord(getConfig().getString("webhookURL"));
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(java.awt.Color.BLUE)
                .setDescription(":speech_balloon: **" + e.getPlayer().getName() + "** >> **" + e.getMessage() + "**")
        );
        try {
            webhook.execute();
        }
        catch(java.io.IOException event) {
            getLogger().severe(event.getStackTrace().toString());
        }
    }

    @EventHandler
    //DEATH MESSAGE
    public void DCdeath(PlayerDeathEvent e) {

        Player p = e.getEntity().getPlayer();

        Discord webhook = new Discord(getConfig().getString("webhookURL"));
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(java.awt.Color.YELLOW)
                .setDescription(getConfig().getString("diemessage").replace("%player%", p.getName()))
        );
        try {
            webhook.execute();
        }
        catch(java.io.IOException event) {
            getLogger().severe(event.getStackTrace().toString());
        }
    }

    @EventHandler
    //JOIN SERVER
    public void DCjoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        Discord webhook = new Discord(getConfig().getString("webhookURL"));
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(Color.GREEN)
                .setDescription("+ **" + p.getName() + "**")
        );
        try {
            webhook.execute();
        }
        catch(java.io.IOException event) {
            getLogger().severe(event.getStackTrace().toString());
        }
    }

    @EventHandler
    //LEAVE SERVER
    public void DCleave(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        Discord webhook = new Discord(getConfig().getString("webhookURL"));;
        webhook.addEmbed(new Discord.EmbedObject()
                .setColor(Color.RED)
                .setDescription("- **" + p.getName() + "**")
        );
        try {
            webhook.execute();
        }
        catch(java.io.IOException event) {
            getLogger().severe(event.getStackTrace().toString());
        }
    }
}

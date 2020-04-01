package de.n1ck145.lobbySystem.listener;

import de.n1ck145.lobbySystem.GUI.GUI_Compass;
import de.n1ck145.lobbySystem.MySQL.API_MySQL;
import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

public class EventManager implements Listener {
    private Main main = Main.getMain();
    private FileManager messages = this.main.getMessages();
    private FileManager compass = this.main.getFileManagerCompass();

    @EventHandler
    public void joinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendTitle(this.main.translateVars(this.messages.getString("join.title"), p), this.main.translateVars(this.messages.getString("join.subtitle"), p));
        e.setJoinMessage(this.main.translateVars(this.messages.getString("join.public-message"), p));
        p.sendMessage(this.main.translateVars(this.messages.getString("join.private-message")));
    }

    @EventHandler
    public void quitMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(this.main.translateVars(this.messages.getString("quit"), p));
    }

    @EventHandler
    public void onLobbyGrief(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getConfig().getStringList("system-enable-worlds").contains(p.getWorld().getName()))
            return;
        if (this.main.getBuildPlayer().contains(p))
            return;
        e.setCancelled(true);
        p.sendMessage(this.main.translateVars(this.messages.getString("error.destroy-lobby"), p));
    }

    @EventHandler
    public void onLobbyGrief(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
            return;
        if (this.main.getBuildPlayer().contains(p))
            return;
        e.setCancelled(true);
        p.sendMessage(this.main.translateVars(this.messages.getString("error.destroy-lobby"), p));
    }

    @EventHandler
    public void removeCanBuild(PlayerQuitEvent e) {
        if (!this.main.getBuildPlayer().contains(e.getPlayer()))
            return;
        this.main.getBuildPlayer().remove(e.getPlayer());
    }

    @EventHandler
    public void givePlayerLobbyCompass(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getFileManagerCompass().getBoolean("get-on-join"))
            return;
        int slot = this.main.getFileManagerCompass().getInt("slot");
        p.getInventory().setItem(slot, this.main.getItem_Compass().get());
    }

    @EventHandler
    public void givePlayerLobbyCompass(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getFileManagerCompass().getBoolean("get-on-join"))
            return;
        int slot = this.main.getFileManagerCompass().getInt("slot");

        p.getInventory().setItem(slot, this.main.getItem_Compass().get());
    }

    @EventHandler
    public void playerMoveItemInInventory(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (this.main.getBuildPlayer().contains(e.getClickedInventory().getHolder()))
            return;
        if (this.main.getConfig().getBoolean("can-move-items-in-inventory"))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent e) {
        if (this.main.getConfig().getBoolean("can-drop-items"))
            return;
        if (this.main.getBuildPlayer().contains(e.getPlayer()))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void openCompassGui(PlayerInteractEvent e) {
        if (!e.getPlayer().getItemInHand().equals(this.main.getItem_Compass().get()))
            return;
        if (e.getAction() == Action.PHYSICAL)
            return;
        Inventory inv = GUI_Compass.getInventory();
        e.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) {
        if (this.main.getConfig().getBoolean("change-foodlevel"))
            return;
        if (e.getFoodLevel() < ((Player) e.getEntity()).getFoodLevel())
            e.setCancelled(true);
    }

    @EventHandler
    public void getDamage(EntityDamageEvent e) {
        if (this.main.getConfig().getBoolean("player-get-damage"))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void compassGuiClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equals(this.main.translateVars(this.main.getFileManagerCompass().getString("gui.name"))))
            return;
        for (String s: this.compass.getConfigurationSection("gui.items").getKeys(false)) {
            String path = "gui.items." + s + ".";
            int slot = this.compass.getInt(String.valueOf(path) + "slot");
            if (e.getSlot() == slot) {
                if (this.compass.getBoolean(String.valueOf(path) + "command-execute-by-player")) {
                    Bukkit.dispatchCommand((CommandSender) e.getWhoClicked(), this.main.translateVars(this.compass.getString(String.valueOf(path) + "command"), (Player) e.getWhoClicked()));
                } else {
                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), this.main.translateVars(this.compass.getString(String.valueOf(path) + "command"), (Player) e.getWhoClicked()));
                }
                return;
            }
        }
    }
    @EventHandler
    public void createFaktSign(SignChangeEvent e) {
        if (this.main.translateVars(e.getLine(0), e.getPlayer()).contains(this.main.getConfig().getString("fakt-signs-header")))
            e.getPlayer().sendMessage(this.main.translateVars(this.messages.getString("fakt-signs.create"), e.getPlayer()));
    }
    @EventHandler
    public void faktSignFind(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (!(e.getClickedBlock().getState() instanceof Sign))
            return;
        Sign s = (Sign) e.getClickedBlock().getState();
        if (this.main.translateVars(s.getLine(0), e.getPlayer()).contains(this.main.getConfig().getString("fakt-signs-header")))
            e.getPlayer().sendMessage(this.main.translateVars(this.messages.getString("fakt-signs.find"), e.getPlayer()));
    }

    @EventHandler
    public void changeWeather(WeatherChangeEvent e) {
        if (this.main.getConfig().getBoolean("change-weather"))
            return;
        e.setCancelled(true);
    }
    @EventHandler
    public void logLastOnline(PlayerQuitEvent e) {
        if (API_MySQL.isConnected()) {
            String uuid = e.getPlayer().getUniqueId().toString();

            API_MySQL.update("UPDATE lobby_lastOnline SET value=NOW() WHERE UUID='" + uuid + "';");
        }
    }
}
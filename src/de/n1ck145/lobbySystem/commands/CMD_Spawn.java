package de.n1ck145.lobbySystem.commands;

import de.n1ck145.lobbySystem.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Spawn implements CommandExecutor {
    private Main main = Main.getMain();

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!this.main.getConfig().getStringList("disable-spawn-command-worlds").contains(p.getWorld().getName())) {
                if (p.hasPermission("lobby.cmd.spawn")) {
                    if (this.main.getLocations().contains("spawn")) {
                        p.teleport(this.main.getLocations().getLocation("spawn"));
                        p.sendMessage(this.main.translateVars(this.main.getMessages().getString("spawn.telport-to-spawn"), p, "/spawn", "lobby.cmd.spawn"));
                    } else
                        p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.spawn-not-set"), p, "/spawn", "lobby.cmd.spawn"));
                } else
                    p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.no-permission"), p, "/spawn", "lobby.cmd.spawn"));
            } else
                p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.lobby-command-disabled"), p, "/spawn", "lobby.cmd.spawn"));
        }
        return false;
    }
}
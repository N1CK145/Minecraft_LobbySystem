package de.n1ck145.lobbySystem.commands;

import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Build implements CommandExecutor {
    Main main = Main.getMain();
    FileManager messages = main.getMessages();

    public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission("lobbySystem.cmd.build")) {
                    if (main.getBuildPlayer().contains(p)) {
                        main.getBuildPlayer().remove(p);
                        p.sendMessage(main.translateVars(this.messages.getString("build-mode.disable")));
                    } else {
                    	main.getBuildPlayer().add(p);
                    	p.sendMessage(main.translateVars(this.messages.getString("build-mode.enable")));
                    }
                } else
                    p.sendMessage(main.translateVars(this.messages.getString("error.no-permission"), p.getName(), "/build", "lobby.cmd.build"));
            } else if (args.length == 1) {
                if (p.hasPermission("lobbySystem.cmd.build.other")) {
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (main.getBuildPlayer().contains(target)) {
                            main.getBuildPlayer().remove(target);
                            target.sendMessage(main.translateVars(this.messages.getString("build-mode.disable")));
                            p.sendMessage(main.translateVars(this.messages.getString("build-mode.other.disable"), p.getName(), "/build [player]", "lobby.cmd.build.other").replace("%target%", target.getName()));
                        } else {
                            main.getBuildPlayer().add(target);
                            target.sendMessage(main.translateVars(this.messages.getString("build-mode.enable")));
                            p.sendMessage(main.translateVars(this.messages.getString("build-mode.other.enable"), p.getName(), "/build [player]", "lobby.cmd.build.other").replace("%target%", target.getName()));
                        }
                    } else
                        p.sendMessage(main.translateVars(this.messages.getString("error.player-not-found"), p.getName(), "/build [player]", "lobby.cmd.build.other").replace("%target%", args[0]));
                } else
                    p.sendMessage(main.translateVars(this.messages.getString("error.no-permission"), p.getName(), "/build [player]", "lobby.cmd.build.other"));
            } else
                p.sendMessage(main.translateVars(this.messages.getString("error.incorrect-syntax"), p.getName(), "/build [player]", "NULL"));
        }
        return false;
    }
}
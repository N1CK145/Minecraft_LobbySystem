package de.n1ck145.lobbySystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;

public class CMD_LobbyC implements CommandExecutor {
    private Main main = Main.getMain();
    private int lastPage = 1;
    private FileManager locations = this.main.getLocations();
    private FileManager messages = this.main.getMessages();


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("lobbySystem.cmd.admin")) {
                if (args.length >= 1) {
                    switch (args[0].toLowerCase()) {
                        case "help":
                            try {
                                int page = Integer.parseInt(args[1]);
                                showHelp(p, page);
                            } catch (Exception e) {
                            	showHelp(p, 1);
                            }
                            return false;
                        case "setspawn":
                            this.locations.setLocation("spawn", p.getLocation(), true);
                            this.locations.save();
                            p.sendMessage(this.main.translateVars(this.messages.getString("spawn.spawn-set"), p.getName(), "/lobbyc setspawn", "lobby.cmd.admin"));
                            return false;
                    }
                    showHelp(p, 1);
                } else
                    p.sendMessage(this.main.translateVars(this.messages.getString("error.incorrect-syntax"), p.getName(), "/lobbyc", "lobby.cmd.admin"));
            } else
                p.sendMessage(this.main.translateVars(this.messages.getString("error.no-permission"), p.getName(), "/lobbyc", "lobby.cmd.admin"));
        }
        return false;
    }

    public void showHelp(Player p, int page) {
        switch (page) {
            case 1:
            	p.sendMessage(main.getPrefix() + "§7----------------[ §6HELP §4[1/" + this.lastPage + "]§7]----------------");
                p.sendMessage(main.getPrefix() + "§6/lobbyc help <page> §b- shows help");
                p.sendMessage(main.getPrefix() + "§6/lobbyc setspawn §b- set the spawn location");
                p.sendMessage(main.getPrefix() + "§6/build [player] §b- allows you or the target player to build");
                p.sendMessage(main.getPrefix() + "§6/warp <warp> §b- teleport you to warp");
                p.sendMessage(main.getPrefix() + "§6/setwarp <warp> §b- Creates new warp or change warps location to players");
                return;
        }
        p.sendMessage(main.getPrefix() + "§cPage §6" + page + " §cnot found!");
    }
}
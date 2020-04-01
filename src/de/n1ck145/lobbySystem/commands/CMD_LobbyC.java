package de.n1ck145.lobbySystem.commands;

import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_LobbyC implements CommandExecutor {
    private Main main = Main.getMain();
    private int lastPage = 1;
    private FileManager locations = this.main.getLocations();
    private FileManager messages = this.main.getMessages();


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("lobby.cmd.admin")) {
                if (args.length >= 1) {
                    String str;
                    switch ((str = args[0].toLowerCase()).hashCode()) {
                        case 3198785:
                            if (!str.equals("help"))
                                break;
                            try {
                                int page = Integer.parseInt(args[1]);
                                help(p, page);
                            } catch (Exception e) {
                                help(p);
                            }
                            return false;
                        case 1433904217:
                            if (!str.equals("setspawn"))
                                break;
                            this.locations.setLocation("spawn", p.getLocation());
                            this.locations.save();
                            p.sendMessage(this.main.translateVars(this.messages.getString("spawn.spawn-set"), p, "/lobbyc setspawn", "lobby.cmd.admin"));
                            return false;
                    }
                    help(p);
                } else
                    p.sendMessage(this.main.translateVars(this.messages.getString("error.incorrect-syntax"), p, "/lobbyc", "lobby.cmd.admin"));
            } else
                p.sendMessage(this.main.translateVars(this.messages.getString("error.no-permission"), p, "/lobbyc", "lobby.cmd.admin"));
        }
        return false;
    }

    public void help(Player p) {
        p.sendMessage(String.valueOf(this.main.getPrefix()) + "§7----------------[ §6HELP §4[1/" + this.lastPage + "]§7]----------------");
        p.sendMessage(String.valueOf(this.main.getPrefix()) + "§6/lobbyc help <page> §b- shows help");
        p.sendMessage(String.valueOf(this.main.getPrefix()) + "§6/lobbyc setspawn §b- set the spawn location");
    }

    public void help(Player p, int page) {
        switch (page) {
            case 1:
                help(p);
                return;
        }
        p.sendMessage(String.valueOf(this.main.getPrefix()) + "§cPage §6" + page + " §cnot found!");
    }
}
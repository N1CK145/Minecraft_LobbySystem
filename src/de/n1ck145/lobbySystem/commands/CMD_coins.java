package de.n1ck145.lobbySystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.n1ck145.lobbySystem.coins.API_Coins;
import de.n1ck145.lobbySystem.main.Main;

public class CMD_Coins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
    	if(sender instanceof Player) {
    		Player p = (Player)sender;
    		
    		if(args.length == 0) {
    			if(!API_Coins.playerExists(p))
        			API_Coins.createPlayer(p);
        		
    			p.sendMessage(Main.getMain().getCoinMessage(p));
    		}
    	}
    	if(args.length > 0) {
			String permission;
			switch (args[0].toLowerCase()) {
			case "set":
				// /coins set <player> <amount>
				permission = "lobby.cmd.coins.set";
				if(sender.hasPermission(permission)) {
					if(args.length == 3) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							try {
								double val = Double.parseDouble(args[2]);
								API_Coins.setCoins(target, val);
								sender.sendMessage(Main.getMain().getCoinMessageSetCoins(target));
							} catch (Exception e) {
								sender.sendMessage(Main.getMain().getErrorMessageCanNotCastNumber(sender.getName(), args[2]));
							}
						}else
							sender.sendMessage(Main.getMain().getErrorMessagePlayerNotFount(sender.getName(), args[1]));
					}else
						sender.sendMessage(Main.getMain().getErrorMessageWrongSyntax(sender.getName(), "/coins set <player> <amount>", permission));						
				}else
					sender.sendMessage(Main.getMain().getErrorMessageNoPermission(sender.getName()));
				break;
				
			case "add":
				// /coins add <player> <amount>
				permission = "lobby.cmd.coins.add";
				if(sender.hasPermission(permission)) {
					if(args.length == 3) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							try {
								double val = Double.parseDouble(args[2]);
								API_Coins.addCoins(target, val);
								sender.sendMessage(Main.getMain().getCoinMessageSetCoins(target));
							} catch (Exception e) {
								sender.sendMessage(Main.getMain().getErrorMessageCanNotCastNumber(sender.getName(), args[2]));
							}
						}else
							sender.sendMessage(Main.getMain().getErrorMessagePlayerNotFount(sender.getName(), args[1]));
					}else
						sender.sendMessage(Main.getMain().getErrorMessageWrongSyntax(sender.getName(), "/coins set <player> <amount>", permission));						
				}else
					sender.sendMessage(Main.getMain().getErrorMessageNoPermission(sender.getName()));
				break;
				
			case "remove":
				// /coins remove <player> <amount>
				permission = "lobby.cmd.coins.remove";
				if(sender.hasPermission(permission)) {
					if(args.length == 3) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							try {
								double val = Double.parseDouble(args[2]);
								API_Coins.removeCoins(target, val);
								sender.sendMessage(Main.getMain().getCoinMessageSetCoins(target));
							} catch (Exception e) {
								sender.sendMessage(Main.getMain().getErrorMessageCanNotCastNumber(sender.getName(), args[2]));
							}
						}else
							sender.sendMessage(Main.getMain().getErrorMessagePlayerNotFount(sender.getName(), args[1]));
					}else
						sender.sendMessage(Main.getMain().getErrorMessageWrongSyntax(sender.getName(), "/coins set <player> <amount>", permission));						
				}else
					sender.sendMessage(Main.getMain().getErrorMessageNoPermission(sender.getName()));
				break;
				
			default:
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target != null)
					sender.sendMessage(Main.getMain().getCoinMessageOthers(target));
				else
					sender.sendMessage(Main.getMain().getErrorMessagePlayerNotFount(sender.getName(), args[0]));
				break;
			}
		}
    	
        return true;
    }
}
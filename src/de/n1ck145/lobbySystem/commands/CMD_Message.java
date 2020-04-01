package de.n1ck145.lobbySystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.n1ck145.lobbySystem.main.Main;

public class CMD_Message implements CommandExecutor{

	private Main main = Main.getMain();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 2) {
			if(Bukkit.getPlayer(args[0]) != null) {
				Player target = Bukkit.getPlayer(args[0]);
				String msg = "";
				
				for(int i = 1; i < args.length; i++) {
					msg += args[i] + " ";
				}
				
				target.sendMessage(main.color(main.getMessages().getString("cmd.message.receive").replace("%sender%", sender.getName()).replace("%message%", msg)));
				sender.sendMessage(main.color(main.translateVars(main.getMessages().getString("cmd.message.send").replace("%target%", target.getName()).replace("%message%", msg))));
			}else
				sender.sendMessage(main.translateVars(main.getMessages().getString("error.player-not-found"), "/message <player> <message>", "lobby.cmd.message").replace("%player%", args[0]));
		}else
			sender.sendMessage(main.translateVars(main.getMessages().getString("error.incorrect-syntax"), "/message <player> <message>", "lobby.cmd.message"));
		return true;
	}

}

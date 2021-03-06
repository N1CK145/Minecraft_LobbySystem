package de.n1ck145.lobbySystem.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.n1ck145.lobbySystem.main.Main;

public class CMD_Warp implements CommandExecutor{

	private Main main = Main.getMain();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return false;
		
		Player p = (Player)sender;
		String permission;
		switch (cmd.getName().toLowerCase()) {
		case "warp":
			permission = "lobbySystem.cmd.warp";
			
			if(sender.hasPermission(permission)) {
				if(args.length == 1) {
					try {
						p.teleport(getWarpLocation(args[0]));
						p.sendMessage(main.translateVars(main.getMessages().getString("cmd.warp.success"), p.getName()).replace("%warp%", args[0]));
					} catch (Exception e) {
						p.sendMessage(main.translateVars(main.getMessages().getString("cmd.warp.invalid"), p.getName()).replace("%warp%", args[0]));
					}
										
				}else
					p.sendMessage(main.getErrorMessageWrongSyntax(p.getName(), "/warp <warp>", permission));
			}else
				sender.sendMessage(main.getErrorMessageNoPermission(p.getName(), permission));
			break;
			
		case "setwarp":
			permission = "lobbySystem.cmd.setwarp";
			
			if(sender.hasPermission(permission)) {
				if(args.length == 1) {
					try {
						setWarpLocation(args[0], p.getLocation());
						p.sendMessage(main.translateVars(main.getMessages().getString("cmd.setwarp.success"), p.getName()).replace("%warp%", args[0]));		
					} catch (Exception e) {
						p.sendMessage(main.translateVars(main.getMessages().getString("cmd.setwarp.invalid"), p.getName()).replace("%warp%", args[0]));
					}
				}else
					p.sendMessage(main.getErrorMessageWrongSyntax(p.getName(), "/warp <warp>", permission));
			}else
				sender.sendMessage(main.getErrorMessageNoPermission(p.getName(), permission));
			break;
		}
		return true;
	}

	private Location getWarpLocation(String warp) {
		return main.getLocations().getLocation("warp." + warp);
	}
	private void setWarpLocation(String warp, Location loc) {
		main.getLocations().setLocation("warp." + warp , loc, true);
		main.getLocations().save();
	}
}

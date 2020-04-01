/*    */ package de.n1ck145.lobbySystem.commands;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CMD_Spawn
/*    */   implements CommandExecutor
/*    */ {
/* 12 */   private Main main = Main.getMain();
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 16 */     if (sender instanceof Player) {
/* 17 */       Player p = (Player)sender;
/* 18 */       if (!this.main.getConfig().getStringList("disable-spawn-command-worlds").contains(p.getWorld().getName()))
/* 19 */       { if (p.hasPermission("lobby.cmd.spawn"))
/* 20 */         { if (this.main.getLocations().contains("spawn")) {
/* 21 */             p.teleport(this.main.getLocations().getLocation("spawn"));
/* 22 */             p.sendMessage(this.main.translateVars(this.main.getMessages().getString("spawn.telport-to-spawn"), p, "/spawn", "lobby.cmd.spawn"));
/*    */           } else {
/* 24 */             p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.spawn-not-set"), p, "/spawn", "lobby.cmd.spawn"));
/*    */           }  }
/* 26 */         else { p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.no-permission"), p, "/spawn", "lobby.cmd.spawn")); }
/*    */          }
/* 28 */       else { p.sendMessage(this.main.translateVars(this.main.getMessages().getString("error.lobby-command-disabled"), p, "/spawn", "lobby.cmd.spawn")); }
/*    */     
/* 30 */     }  return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\commands\CMD_Spawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
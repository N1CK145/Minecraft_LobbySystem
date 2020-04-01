/*    */ package de.n1ck145.lobbySystem.commands;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import de.n1ck145.lobbySystem.utils.FileManager;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CMD_Build
/*    */   implements CommandExecutor
/*    */ {
/* 14 */   Main main = Main.getMain();
/* 15 */   FileManager messages = this.main.getMessages();
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
/* 19 */     if (sender instanceof Player) {
/* 20 */       Player p = (Player)sender;
/* 21 */       if (args.length == 0)
/* 22 */       { if (p.hasPermission("lobby.cmd.build"))
/* 23 */         { if (this.main.getBuildPlayer().contains(p)) {
/* 24 */             this.main.getBuildPlayer().remove(p);
/* 25 */             p.sendMessage(this.main.translateVars(this.messages.getString("build-mode.disable")));
/*    */           } else {
/* 27 */             this.main.getBuildPlayer().add(p);
/* 28 */             p.sendMessage(this.main.translateVars(this.messages.getString("build-mode.enable")));
/*    */           }  }
/*    */         else
/* 31 */         { p.sendMessage(this.main.translateVars(this.messages.getString("error.no-permission"), p, "/build", "lobby.cmd.build")); }  }
/* 32 */       else if (args.length == 1)
/* 33 */       { if (p.hasPermission("lobby.cmd.build.other"))
/* 34 */         { if (Bukkit.getPlayerExact(args[0]) != null) {
/* 35 */             Player target = Bukkit.getPlayerExact(args[0]);
/* 36 */             if (this.main.getBuildPlayer().contains(target)) {
/* 37 */               this.main.getBuildPlayer().remove(target);
/* 38 */               target.sendMessage(this.main.translateVars(this.messages.getString("build-mode.disable")));
/* 39 */               p.sendMessage(this.main.translateVars(this.messages.getString("build-mode.other.disable"), p, "/build [player]", "lobby.cmd.build.other")
/* 40 */                   .replace("%target%", target.getName()));
/*    */             } else {
/* 42 */               this.main.getBuildPlayer().add(target);
/* 43 */               target.sendMessage(this.main.translateVars(this.messages.getString("build-mode.enable")));
/* 44 */               p.sendMessage(this.main.translateVars(this.messages.getString("build-mode.other.enable"), p, "/build [player]", "lobby.cmd.build.other")
/* 45 */                   .replace("%target%", target.getName()));
/*    */             } 
/*    */           } else {
/* 48 */             p.sendMessage(this.main.translateVars(this.messages.getString("error.player-not-found"), p, "/build [player]", "lobby.cmd.build.other")
/* 49 */                 .replace("%target%", args[0]));
/*    */           }  }
/* 51 */         else { p.sendMessage(this.main.translateVars(this.messages.getString("error.no-permission"), p, "/build [player]", "lobby.cmd.build.other")); }
/*    */          }
/* 53 */       else { p.sendMessage(this.main.translateVars(this.messages.getString("error.incorrect-syntax"), p, "/build [player]", "NULL")); }
/*    */     
/* 55 */     }  return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\commands\CMD_Build.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
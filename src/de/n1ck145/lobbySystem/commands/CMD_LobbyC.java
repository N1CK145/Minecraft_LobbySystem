/*    */ package de.n1ck145.lobbySystem.commands;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import de.n1ck145.lobbySystem.utils.FileManager;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CMD_LobbyC
/*    */   implements CommandExecutor
/*    */ {
/* 13 */   private Main main = Main.getMain();
/* 14 */   private int lastPage = 1;
/* 15 */   private FileManager locations = this.main.getLocations();
/* 16 */   private FileManager messages = this.main.getMessages();
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 20 */     if (sender instanceof Player)
/* 21 */     { Player p = (Player)sender;
/* 22 */       if (p.hasPermission("lobby.cmd.admin"))
/* 23 */       { if (args.length >= 1)
/* 24 */         { String str; switch ((str = args[0].toLowerCase()).hashCode()) { case 3198785: if (!str.equals("help")) {
/*    */                 break;
/*    */               }
/*    */ 
/*    */ 
/*    */ 
/*    */               
/*    */               try {
/* 32 */                 int page = Integer.parseInt(args[1]);
/* 33 */                 help(p, page);
/* 34 */               } catch (Exception e) {
/* 35 */                 help(p);
/*    */               } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 47 */               return false;case 1433904217: if (!str.equals("setspawn")) break;  this.locations.setLocation("spawn", p.getLocation()); this.locations.save(); p.sendMessage(this.main.translateVars(this.messages.getString("spawn.spawn-set"), p, "/lobbyc setspawn", "lobby.cmd.admin")); return false; }  help(p); } else { p.sendMessage(this.main.translateVars(this.messages.getString("error.incorrect-syntax"), p, "/lobbyc", "lobby.cmd.admin")); }  } else { p.sendMessage(this.main.translateVars(this.messages.getString("error.no-permission"), p, "/lobbyc", "lobby.cmd.admin")); }  }  return false;
/*    */   }
/*    */   
/*    */   public void help(Player p) {
/* 51 */     p.sendMessage(String.valueOf(this.main.getPrefix()) + "§7----------------[ §6HELP §4[1/" + this.lastPage + "]§7]----------------");
/* 52 */     p.sendMessage(String.valueOf(this.main.getPrefix()) + "§6/lobbyc help <page> §b- shows help");
/* 53 */     p.sendMessage(String.valueOf(this.main.getPrefix()) + "§6/lobbyc setspawn §b- set the spawn location");
/*    */   }
/*    */   
/*    */   public void help(Player p, int page) {
/* 57 */     switch (page) {
/*    */       case 1:
/* 59 */         help(p);
/*    */         return;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     p.sendMessage(String.valueOf(this.main.getPrefix()) + "§cPage §6" + page + " §cnot found!");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\commands\CMD_LobbyC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
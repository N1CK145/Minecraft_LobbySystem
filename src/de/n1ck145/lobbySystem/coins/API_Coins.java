/*    */ package de.n1ck145.lobbySystem.coins;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.MySQL.API_MySQL;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class API_Coins
/*    */ {
/*    */   public static boolean playerExists(Player p) {
/* 13 */     String uuid = p.getUniqueId().toString();
/*    */     
/*    */     try {
/* 16 */       ResultSet rs = API_MySQL.getResult("SELECT * FROM lobby_coins WHERE UUID= '" + uuid + "';");
/* 17 */       if (rs.next()) {
/* 18 */         return (rs.getString("UUID") != null);
/*    */       }
/* 20 */     } catch (Exception exception) {}
/*    */     
/* 22 */     return false;
/*    */   }
/*    */   public static void createPlayer(Player p) {
/* 25 */     if (!playerExists(p)) {
/* 26 */       String uuid = p.getUniqueId().toString();
/* 27 */       String name = p.getName();
/*    */       
/* 29 */       API_MySQL.update("INSERT INTO lobby_coins(UUID, UserName, coins)VALUES('" + 
/* 30 */           uuid + "', '" + name + "', 0.0);");
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void addCoins(Player p, double val) {
/* 35 */     setCoins(p, getCoins(p) + val);
/*    */   }
/*    */   
/*    */   public static void removeCoins(Player p, double val) {
/* 39 */     setCoins(p, getCoins(p) - val);
/*    */   }
/*    */   
/*    */   public static void setCoins(Player p, double val) {
/* 43 */     String uuid = p.getUniqueId().toString();
/*    */     
/* 45 */     if (playerExists(p)) {
/* 46 */       API_MySQL.update("UPDATE lobby_coins SET coins= '" + val + "' WHERE UUID= '" + uuid + "';");
/*    */     } else {
/* 48 */       createPlayer(p);
/* 49 */       setCoins(p, val);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static double getCoins(Player p) {
/* 54 */     String uuid = p.getUniqueId().toString();
/* 55 */     double coins = 0.0D;
/*    */     
/* 57 */     if (playerExists(p)) {
/*    */       try {
/* 59 */         ResultSet rs = API_MySQL.getResult("SELECT * FROM lobby_coins WHERE UUID= '" + uuid + "';");
/* 60 */         if (rs.next()) {
/* 61 */           coins = rs.getDouble("coins");
/*    */         }
/* 63 */       } catch (SQLException e) {
/* 64 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/* 67 */       createPlayer(p);
/* 68 */       return getCoins(p);
/*    */     } 
/* 70 */     return coins;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\coins\API_Coins.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
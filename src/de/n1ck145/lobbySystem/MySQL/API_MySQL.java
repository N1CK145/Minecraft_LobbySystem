/*    */ package de.n1ck145.lobbySystem.MySQL;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class API_MySQL
/*    */ {
/*    */   public static String host;
/*    */   public static String port;
/*    */   public static String database;
/*    */   public static String username;
/*    */   public static String password;
/*    */   public static Connection con;
/*    */   
/*    */   public static void connect() {
/* 23 */     if (!isConnected())
/*    */       try {
/* 25 */         con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
/* 26 */         Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§aMySQL connencted!");
/* 27 */       } catch (SQLException e) {
/* 28 */         e.printStackTrace();
/* 29 */         Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL connect Error!");
/*    */       }  
/*    */   }
/*    */   
/*    */   public static void disconnect() {
/* 34 */     if (isConnected())
/*    */       try {
/* 36 */         con.close();
/* 37 */       } catch (SQLException e) {
/* 38 */         e.printStackTrace();
/* 39 */         Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL disconnet error!");
/*    */       }  
/*    */   }
/*    */   
/*    */   public static boolean isConnected() {
/* 44 */     return !(con == null);
/*    */   }
/*    */   public static void update(String querry) {
/*    */     try {
/* 48 */       PreparedStatement ps = con.prepareStatement(querry);
/* 49 */       ps.executeUpdate();
/* 50 */     } catch (SQLException e) {
/* 51 */       e.printStackTrace();
/* 52 */       Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL update error!");
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ResultSet getResult(String querry) {
/*    */     try {
/* 58 */       PreparedStatement ps = con.prepareStatement(querry);
/* 59 */       return ps.executeQuery();
/* 60 */     } catch (SQLException e) {
/* 61 */       e.printStackTrace();
/* 62 */       Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL get-result error!");
/*    */       
/* 64 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\MySQL\API_MySQL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
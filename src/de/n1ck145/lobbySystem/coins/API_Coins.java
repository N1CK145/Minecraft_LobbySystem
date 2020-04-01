package de.n1ck145.lobbySystem.coins;

import de.n1ck145.lobbySystem.MySQL.API_MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;



public class API_Coins
{
  public static boolean playerExists(Player p) {
    String uuid = p.getUniqueId().toString();
    
    try {
      ResultSet rs = API_MySQL.getResult("SELECT * FROM lobby_coins WHERE UUID= '" + uuid + "';");
      if (rs.next()) {
        return (rs.getString("UUID") != null);
      }
    } catch (Exception exception) {}
    
    return false;
  }
  public static void createPlayer(Player p) {
    if (!playerExists(p)) {
      String uuid = p.getUniqueId().toString();
      String name = p.getName();
      
      API_MySQL.update("INSERT INTO lobby_coins(UUID, UserName, coins)VALUES('" + 
          uuid + "', '" + name + "', 0.0);");
    } 
  }
  
  public static void addCoins(Player p, double val) {
    setCoins(p, getCoins(p) + val);
  }
  
  public static void removeCoins(Player p, double val) {
    setCoins(p, getCoins(p) - val);
  }
  
  public static void setCoins(Player p, double val) {
    String uuid = p.getUniqueId().toString();
    
    if (playerExists(p)) {
      API_MySQL.update("UPDATE lobby_coins SET coins= '" + val + "' WHERE UUID= '" + uuid + "';");
    } else {
      createPlayer(p);
      setCoins(p, val);
    } 
  }
  
  public static double getCoins(Player p) {
    String uuid = p.getUniqueId().toString();
    double coins = 0.0D;
    
    if (playerExists(p)) {
      try {
        ResultSet rs = API_MySQL.getResult("SELECT * FROM lobby_coins WHERE UUID= '" + uuid + "';");
        if (rs.next()) {
          coins = rs.getDouble("coins");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } else {
      createPlayer(p);
      return getCoins(p);
    } 
    return coins;
  }
}
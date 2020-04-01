/*    */ package de.n1ck145.lobbySystem.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*    */ 
/*    */ public class FileManager
/*    */ {
/*    */   private File f;
/*    */   private YamlConfiguration conf;
/*    */   
/*    */   public FileManager(String path, String fileName) {
/* 19 */     this.f = new File(path, fileName);
/* 20 */     this.conf = YamlConfiguration.loadConfiguration(this.f);
/*    */   }
/*    */ 
/*    */   
/*    */   public void save() {
/*    */     try {
/* 26 */       this.conf.save(this.f);
/* 27 */     } catch (IOException e) {
/* 28 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String path, Object val) {
/* 34 */     this.conf.set(path, val);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt(String path) {
/* 39 */     return this.conf.getInt(path);
/*    */   }
/*    */   public List<Integer> getIntegerList(String path) {
/* 42 */     return this.conf.getIntegerList(path);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble(String path) {
/* 47 */     return this.conf.getDouble(path);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getString(String path) {
/* 52 */     return this.conf.getString(path);
/*    */   }
/*    */   public List<String> getStringList(String path) {
/* 55 */     return this.conf.getStringList(path);
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String path) {
/* 59 */     return this.conf.getBoolean(path);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLocation(String path, Location loc) {
/* 64 */     this.conf.set(String.valueOf(path) + ".World", loc.getWorld().getName());
/* 65 */     this.conf.set(String.valueOf(path) + ".X", Double.valueOf(loc.getX()));
/* 66 */     this.conf.set(String.valueOf(path) + ".Y", Double.valueOf(loc.getY()));
/* 67 */     this.conf.set(String.valueOf(path) + ".Z", Double.valueOf(loc.getZ()));
/*    */   }
/*    */   public Location getLocation(String path) {
/* 70 */     World world = Bukkit.getWorld(this.conf.getString(String.valueOf(path) + ".World"));
/* 71 */     double x = this.conf.getDouble(String.valueOf(path) + ".X");
/* 72 */     double y = this.conf.getDouble(String.valueOf(path) + ".Y");
/* 73 */     double z = this.conf.getDouble(String.valueOf(path) + ".Z");
/*    */     
/* 75 */     return new Location(world, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfigurationOptions options() {
/* 80 */     return this.conf.options();
/*    */   }
/*    */   public void addDefault(String path, Object val) {
/* 83 */     this.conf.addDefault(path, val);
/*    */   }
/*    */   public boolean contains(String path) {
/* 86 */     return this.conf.contains(path);
/*    */   }
/*    */   public ConfigurationSection getConfigurationSection(String path) {
/* 89 */     return this.conf.getConfigurationSection(path);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySyste\\utils\FileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package de.n1ck145.lobbySystem.GUI;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import de.n1ck145.lobbySystem.utils.FileManager;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GUI_Compass
/*    */ {
/* 17 */   private Main main = Main.getMain();
/*    */   
/*    */   private static Inventory inv;
/*    */   private String name;
/*    */   private int rows;
/*    */   private FileManager config;
/*    */   
/*    */   public GUI_Compass() {
/* 25 */     this.config = this.main.getFileManagerCompass();
/*    */     
/* 27 */     this.name = this.config.getString("gui.name");
/* 28 */     this.rows = this.config.getInt("gui.rows");
/*    */ 
/*    */     
/* 31 */     inv = Bukkit.getServer().createInventory(null, this.rows * 9, this.main.translateVars(this.name));
/*    */ 
/*    */     
/* 34 */     for (String s : this.config.getConfigurationSection("gui.items").getKeys(false)) {
/* 35 */       addItem(s);
/*    */     }
/*    */   }
/*    */   
/*    */   private void addItem(String path) {
/* 40 */     ConfigurationSection item_config = this.main.getFileManagerCompass().getConfigurationSection("gui.items." + path);
/* 41 */     Material material = Material.getMaterial(item_config.getString("material"));
/* 42 */     String name = this.main.translateVars(item_config.getString("display-name"));
/* 43 */     int slot = item_config.getInt("slot");
/* 44 */     ArrayList<String> lore = new ArrayList<>();
/*    */     
/* 46 */     for (String s : item_config.getStringList("lore")) {
/* 47 */       lore.add(this.main.translateVars(s));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     ItemStack item = new ItemStack(material);
/* 54 */     ItemMeta imeta = item.getItemMeta();
/*    */     
/* 56 */     imeta.setDisplayName(name);
/* 57 */     imeta.setLore(lore);
/*    */     
/* 59 */     item.setItemMeta(imeta);
/* 60 */     inv.setItem(slot, item);
/*    */   }
/*    */   
/*    */   public static Inventory getInventory() {
/* 64 */     return inv;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\GUI\GUI_Compass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
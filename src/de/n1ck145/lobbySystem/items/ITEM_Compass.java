/*    */ package de.n1ck145.lobbySystem.items;
/*    */ 
/*    */ import de.n1ck145.lobbySystem.main.Main;
/*    */ import de.n1ck145.lobbySystem.utils.FileManager;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ITEM_Compass
/*    */ {
/*    */   private Main main;
/*    */   private FileManager fm_compass;
/*    */   private ItemStack compass;
/*    */   private ItemMeta compassMeta;
/* 20 */   private ArrayList<String> lore = new ArrayList<>();
/*    */   private String name;
/*    */   
/*    */   public ITEM_Compass(Material material) {
/* 24 */     this.main = Main.getMain();
/* 25 */     this.compass = new ItemStack(material);
/* 26 */     this.fm_compass = this.main.getFileManagerCompass();
/* 27 */     this.compassMeta = this.compass.getItemMeta();
/* 28 */     this.name = this.fm_compass.getString("compass.display-name");
/*    */ 
/*    */     
/* 31 */     this.compassMeta.setDisplayName(this.main.translateVars(this.name));
/* 32 */     for (String s : this.fm_compass.getStringList("compass.lore")) {
/* 33 */       this.lore.add(this.main.translateVars(s));
/*    */     }
/*    */     
/* 36 */     for (String s : this.fm_compass.getStringList("compass.enchantments")) {
/* 37 */       int lvl = Integer.parseInt(s.split(":")[1]);
/* 38 */       String enchantment = s.split(":")[0];
/* 39 */       this.compassMeta.addEnchant(Enchantment.getByName(enchantment), lvl, true);
/*    */     } 
/*    */     
/* 42 */     this.compass.setItemMeta(this.compassMeta);
/*    */   }
/*    */   
/*    */   public ItemStack get() {
/* 46 */     return this.compass;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\items\ITEM_Compass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
package de.n1ck145.lobbySystem.items;

import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ITEM_Compass {
    private Main main;
    private FileManager fm_compass;
    private ItemStack compass;
    private ItemMeta compassMeta;
    private ArrayList < String > lore = new ArrayList < > ();
    private String name;

    public ITEM_Compass(Material material) {
        this.main = Main.getMain();
        this.compass = new ItemStack(material);
        this.fm_compass = this.main.getFileManagerCompass();
        this.compassMeta = this.compass.getItemMeta();
        this.name = this.fm_compass.getString("compass.display-name");
        this.compassMeta.setDisplayName(this.main.translateVars(this.name));

        for (String s: this.fm_compass.getStringList("compass.lore")) {
            this.lore.add(this.main.translateVars(s));
        }

        for (String s: this.fm_compass.getStringList("compass.enchantments")) {
            int lvl = Integer.parseInt(s.split(":")[1]);
            String enchantment = s.split(":")[0];
            this.compassMeta.addEnchant(Enchantment.getByName(enchantment), lvl, true);
        }

        this.compass.setItemMeta(this.compassMeta);
    }

    public ItemStack get() {
        return this.compass;
    }
}
package de.n1ck145.lobbySystem.GUI;

import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI_Compass {
    private Main main = Main.getMain();

    private static Inventory inv;
    private String name;
    private int rows;
    private FileManager config;

    public GUI_Compass() {
        this.config = this.main.getFileManagerCompass();

        this.name = this.config.getString("gui.name");
        this.rows = this.config.getInt("gui.rows");

        inv = Bukkit.getServer().createInventory(null, this.rows * 9, this.main.translateVars(this.name));

        for (String s: this.config.getConfigurationSection("gui.items").getKeys(false)) {
            addItem(s);
        }
    }

    private void addItem(String path) {
        ConfigurationSection item_config = this.main.getFileManagerCompass().getConfigurationSection("gui.items." + path);
        Material material = Material.getMaterial(item_config.getString("material"));
        String name = this.main.translateVars(item_config.getString("display-name"));
        int slot = item_config.getInt("slot");
        ArrayList < String > lore = new ArrayList < > ();

        for (String s: item_config.getStringList("lore")) {
            lore.add(this.main.translateVars(s));
        }

        ItemStack item = new ItemStack(material);
        ItemMeta imeta = item.getItemMeta();

        imeta.setDisplayName(name);
        imeta.setLore(lore);

        item.setItemMeta(imeta);
        inv.setItem(slot, item);
    }

    public static Inventory getInventory() {
        return inv;
    }
}
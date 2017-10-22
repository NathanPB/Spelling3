package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 10/13/17.
 */
@ItemInitializator
public class MerchantRecipeIcon extends SpellingItem{
    public MerchantRecipeIcon(){
        setItemStack(new ItemStack(Material.EMERALD));
        setDisplayName(ChatColor.GREEN+"Merchant Trade");
        addLore("<-- INPUT");
        addLore("OUTPUT ->");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
    }
}

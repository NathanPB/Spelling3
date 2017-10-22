package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/14/17.
 */
@ItemInitializator
public class BlankSpace extends SpellingItem implements Listener{
    public BlankSpace(){
        setItemStack(new ItemStack(Material.STAINED_GLASS_PANE));
        setDisplayName(" ");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
    }
}

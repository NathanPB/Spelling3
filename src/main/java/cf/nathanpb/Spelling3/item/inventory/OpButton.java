package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/14/17.
 */
@ItemInitializator
public class OpButton extends SpellingItem implements Listener{
    public OpButton(){
        setItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
        setDisplayName(ChatColor.GREEN+"Got it!");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
        SpellingItem item = null;
        try {
            item = (SpellingItem) BookEntry.TempMeta.get(e.getWhoClicked().getUniqueId().toString());
        }catch (Exception e2){ e2.printStackTrace();}
        e.getWhoClicked().getInventory().addItem(item.getItemStack());
    }
}

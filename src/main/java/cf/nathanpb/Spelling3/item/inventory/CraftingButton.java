package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class CraftingButton extends SpellingItem implements Listener{
    public CraftingButton(){
        setItemStack(new ItemStack(Material.ANVIL));
        setDisplayName(ChatColor.BLUE+"SpellingRecipe");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
        SpellingItem item = null;
        try {
            item = (SpellingItem) BookEntry.TempMeta.get(e.getWhoClicked().getUniqueId().toString());
            BookEntry.TempMeta.remove(e.getWhoClicked().getUniqueId().toString());
        }catch (Exception e2){ e2.printStackTrace();}
        item.getSpellingRecipe().getHolder().open((Player) e.getWhoClicked());
    }
}

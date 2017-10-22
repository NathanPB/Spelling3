package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.misc.SpellBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/14/17.
 */
@ItemInitializator
public class ReturnButton extends SpellingItem implements Listener{
    public ReturnButton(){
        setItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
        setDisplayName(ChatColor.RED+"Main Menu");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
        SpellingItem.getInstance(SpellBook.class).openBook((Player) e.getWhoClicked());
    }
}

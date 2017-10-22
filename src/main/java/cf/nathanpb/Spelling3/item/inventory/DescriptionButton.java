package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.utils.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class DescriptionButton extends SpellingItem implements Listener{
    public DescriptionButton(){
        setItemStack(new ItemStack(Material.WRITTEN_BOOK));
        setDisplayName(ChatColor.BLUE+"Description");
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
        SpellingItem item = null;
        try{
            item = (SpellingItem) BookEntry.TempMeta.get(e.getWhoClicked().getUniqueId().toString());
            BookEntry.TempMeta.remove(e.getWhoClicked().getUniqueId().toString());
        }catch (Exception e2){ e2.printStackTrace();}
        ItemStack book = InventoryUtils.makeBook("Description", "NathanPB", ((BookItem)item).getBookEntryText(((CraftPlayer)e.getWhoClicked()).getLocale()));
        new SpellingPlayer((Player) e.getWhoClicked()).forceUse(book);
    }
}

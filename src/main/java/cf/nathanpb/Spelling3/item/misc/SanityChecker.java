package cf.nathanpb.Spelling3.item.misc;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Misc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class SanityChecker extends BookItem {
    public SanityChecker(){
        setItemStack(new ItemStack(Material.WATCH));
        setDisplayName(ChatColor.GOLD+"Sanity Checker");
        addLore("Some times is a good idea check where are you going");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent e) {
        super.onRightClick(e);
        e.getPlayer().sendMessage(new SpellingPlayer(e.getPlayer()).getInfo());
    }

    @Override
    public BookArea getArea() {
        return SpellingItem.getInstance(Misc.class);
    }
}

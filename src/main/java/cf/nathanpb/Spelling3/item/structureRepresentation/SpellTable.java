package cf.nathanpb.Spelling3.item.structureRepresentation;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Structures;
import cf.nathanpb.Spelling3.utils.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 10/14/17.
 */
@ItemInitializator
public class SpellTable extends BookItem{
    public SpellTable(){
        setItemStack(new ItemStack(Material.BONE_BLOCK));
        setDisplayName(ChatColor.GOLD+"Spell Table");
        setDescription("We need a safe place to make spells, right?");
    }

    @Override
    public BookEntry getHolder(Player p) {
        return new BookEntry(InventoryUtils.makeBook("spell", "NathanPB", getBookEntryText(p.getLocale())));
    }

    @Override
    public BookArea getArea() {
        return SpellingItem.getInstance(Structures.class);
    }
}

package cf.nathanpb.Spelling3.item.inventory.spellArea;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.ReturnButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 10/13/17.
 */
@ItemInitializator(priority = -2)
public class Food extends BookArea {

    public Food(){
        setItemStack(new ItemStack(Material.APPLE));
        setDisplayName(ChatColor.GOLD+"Foods");
        addLore("Eat spinach, kids!");
    }

    @Override
    public void setup() {
        int var = 0;
        for (int i = getAreaItems().size() - 1; i >= 0; i--) {
            if (i % 7 == 0) {
                var += 9;
            }
        }
        var += 18;
        holder = new BookEntry(ChatColor.BLUE + "Food", var / 9);
        holder.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_LEFT);
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_RIGHT);
        for (SpellingItem item : getAreaItems()) getHolder().addButton(item);
    }
}

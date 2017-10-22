package cf.nathanpb.Spelling3.recipes;

import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.eventHandler.InventoryClick;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.ReturnButton;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/15/17.
 */
public class RecipeSpellTable implements SpellingRecipe {
    private SpellingItem result;
    private ItemStack[] shape;
    public RecipeSpellTable(SpellingItem result){
        this.result = result;
    }

    public RecipeSpellTable shape(ItemStack... shape){
        this.shape = shape;
        return this;
    }
    public ItemStack[] getShape(){
        return this.shape;
    }

    @Override
    public BookEntry getHolder() {
        BookEntry entry = new BookEntry(ChatColor.BLUE+"Spell Table Recipe: "+ChatColor.GOLD+getResult().getItemMeta().getDisplayName(), 5);

        entry.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 11);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 15);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 19);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 20);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 24);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 25);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 29);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 33);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 36);

        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 0);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 1);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 7);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 8);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 9);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 10);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 16);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 17);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 27);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 28);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 34);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 35);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 36);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 37);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 43);
        entry.addButton(SpellingItem.getInstance(ReturnButton.class), 44);


        putSafe(entry, shape[0], 12);
        putSafe(entry, shape[1], 13);
        putSafe(entry, shape[2], 14);
        putSafe(entry, shape[3], 21);
        putSafe(entry, shape[4], 22);
        putSafe(entry, shape[5], 23);
        putSafe(entry, shape[6], 30);
        putSafe(entry, shape[7], 31);
        putSafe(entry, shape[8], 32);

        InventoryClick.block(entry);

        return entry;

    }
    private void putSafe(BookEntry entry, ItemStack stack, int slot){
        if(stack != null) entry.addButton(stack, slot);
    }


    @Override
    public SpellingItem getSResult() {
        return this.result;
    }

    @Override
    public boolean match(ItemStack[] items) {
        for(int i=0; i<items.length; i++){
            if(items[i] == null){
                return getShape()[i] == null;
            } else {
                if (!items[i].isSimilar(getShape()[i])) return false;
            }
        }
        return true;
    }
}

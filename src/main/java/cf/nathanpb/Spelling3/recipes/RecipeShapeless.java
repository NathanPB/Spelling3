package cf.nathanpb.Spelling3.recipes;

import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.eventHandler.InventoryClick;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.ReturnButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * Created by nathanpb on 10/14/17.
 */
public class RecipeShapeless extends ShapelessRecipe implements SpellingRecipe{

    public RecipeShapeless(SpellingItem item){
        super(item.getItemStack());
    }

    @Override
    public RecipeShapeless addIngredient(Material ingredient) {
        super.addIngredient(ingredient);
        return this;
    }

    @Override
    public BookEntry getHolder() {
        BookEntry entry = new BookEntry(ChatColor.BLUE+"Shaped Recipe: "+ChatColor.GOLD+getResult().getItemMeta().getDisplayName(), 5);

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


        putSafe(entry, getIngredientList().get(0), 12);
        putSafe(entry, getIngredientList().get(1), 13);
        putSafe(entry, getIngredientList().get(2), 14);
        putSafe(entry, getIngredientList().get(3), 21);
        putSafe(entry, getIngredientList().get(4), 22);
        putSafe(entry, getIngredientList().get(5), 23);
        putSafe(entry, getIngredientList().get(6), 30);
        putSafe(entry, getIngredientList().get(7), 31);
        putSafe(entry, getIngredientList().get(8), 32);

        InventoryClick.block(entry);

        return entry;

    }

    @Override
    public SpellingItem getSResult() {
        return null;
    }

    @Override
    public void register() {
        Bukkit.addRecipe(this);
    }
    private void putSafe(BookEntry entry, ItemStack stack, int slot){
        if(stack != null) entry.addButton(stack, slot);
    }


}

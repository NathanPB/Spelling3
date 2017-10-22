package cf.nathanpb.Spelling3.recipes;

import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 9/15/17.
 */
public interface SpellingRecipe {
    BookEntry getHolder();
    SpellingItem getSResult();
    default ItemStack getResult(){
        return getSResult().getItemStack();
    }
    default void register(){
        Core.RegisteredRecipes.add(this);
    }
    default boolean match(ItemStack[] items){
        return false;
    }
}

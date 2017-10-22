package cf.nathanpb.Spelling3.item.misc;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.recipes.RecipeShapeless;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by nathanpb on 9/14/17.
 */
@ItemInitializator
public class SpellBook extends SpellingItem implements Listener{
    public SpellBook(){
        setItemStack(new ItemStack(Material.ENCHANTED_BOOK));
        setDisplayName(ChatColor.GOLD+"Mage's Guide");
        addLore("An old book that once belonged to a magician");

        setRecipe(new RecipeShapeless(this).addIngredient(Material.BOOK));
    }

    @Override
    protected void onRightClick(PlayerInteractEvent e) {
        super.onRightClick(e);
        openBook(e.getPlayer());
    }
    public void openBook(Player p){
        BookEntry holder = new BookEntry(ChatColor.DARK_PURPLE+"Spelling - Mage's Guide", 3);
        holder.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        SpellingItem.getItems().stream().filter(i -> i instanceof BookArea).forEach(i ->{
            holder.addButton(i);
            ((BookArea)i).setup();
        } );
        holder.open(p);
    }
}

package cf.nathanpb.Spelling3.item.inventory;

import cf.nathanpb.Spelling3.item.ItemInitializator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 10/14/17.
 */
@ItemInitializator
public class SpellTableCraftButton extends BlankSpace{
    public SpellTableCraftButton(){
        setItemStack(new ItemStack(Material.END_CRYSTAL));
        setDisplayName(ChatColor.LIGHT_PURPLE+"Assembly");
        addLore("Click here to assembly it!");
    }
}

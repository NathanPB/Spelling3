package cf.nathanpb.Spelling3.item.material;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Misc;
import cf.nathanpb.Spelling3.recipes.VillagerTrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

/**
 * Created by nathanpb on 10/13/17.
 */
@ItemInitializator(priority = -1)
public class KnowledgeFragment extends BookItem{
    public KnowledgeFragment(){
        setItemStack(new org.bukkit.inventory.ItemStack(Material.PAPER));
        setDisplayName(ChatColor.GOLD+"Knowledge Fragment");
        addLore("Lost knowledge, written by an old magician!");

        ItemStack s = getItemStack();
        s.setAmount(8);

        setRecipe(new VillagerTrade(new ItemStack(Material.PAPER, 8), new ItemStack(Material.EMERALD), s)
        .addProfession(Villager.Profession.PRIEST)
        .setChance(25));
    }

    @Override
    public BookArea getArea() {
        return SpellingItem.getInstance(Misc.class);
    }


}

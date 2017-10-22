package cf.nathanpb.Spelling3.item.food;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Food;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class ManaCookie extends BookItem{
    public ManaCookie(){
        setItemStack(new ItemStack(Material.COOKIE));
        addLore("Hey! Get a healthy feeding!");
    }

    @Override
    protected void onEaten(PlayerItemConsumeEvent e) {
        super.onEaten(e);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setSaturation(20);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*30, 1));
    }

    @Override
    public BookArea getArea() {
        return SpellingItem.getInstance(Food.class);
    }
}

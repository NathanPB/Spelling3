package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class Sanitatum extends Spell{
    public Sanitatum() {
        setItemStack(new ItemStack(Material.SPECKLED_MELON));
        setManaCost(20);
        setDescription("\"My Immortal\"");
        setDisplayName(ChatColor.GOLD + "Sanitatum");

        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
        Triggers.add(ActionType.CLICK_ENTITY);
    }

    @Override
    public boolean preExecute(Object... args) {
        if(entity.getEntity().hasPotionEffect(PotionEffectType.WITHER)) setManaCost(getManaCost()+60);
        if(entity.getEntity().hasPotionEffect(PotionEffectType.POISON)) setManaCost(getManaCost()+40);
        if(entity.getEntity().hasPotionEffect(PotionEffectType.HUNGER)) setManaCost(getManaCost()+20);
        return super.preExecute(e, args);
    }

    @Override
    public void execute(Object... args) {
        if(entity.getEntity().hasPotionEffect(PotionEffectType.POISON)) entity.getEntity().removePotionEffect(PotionEffectType.POISON);
        if(entity.getEntity().hasPotionEffect(PotionEffectType.WITHER)) entity.getEntity().removePotionEffect(PotionEffectType.WITHER);
        if(entity.getEntity().hasPotionEffect(PotionEffectType.HUNGER)) {
            entity.getEntity().removePotionEffect(PotionEffectType.HUNGER);
            entity.getEntity().sendMessage("you are not a commie anymore");
        }
        super.execute(e);
    }

}

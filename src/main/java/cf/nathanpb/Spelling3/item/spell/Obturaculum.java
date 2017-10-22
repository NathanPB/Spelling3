package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class Obturaculum extends Spell{
    public Obturaculum() {
        setItemStack(new ItemStack(Material.PAPER));
        setManaCost(100);
        setDescription("Stop following me!");
        setDisplayName(ChatColor.GOLD + "Obturaculum");

        Triggers.add(ActionType.CLICK_ENTITY);
    }

    @Override
    public void execute(Object... args) {
        super.execute();
        final Location location = new Location(e.getClickedEntity().getWorld(), e.getClickedEntity().getLocation().getX(),
                e.getClickedEntity().getLocation().getY()+1, e.getClickedEntity().getLocation().getZ());
        e.getClickedEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 10));
        e.getClickedEntity().getWorld().playEffect(location, Effect.SMOKE, 2003);
    }
}

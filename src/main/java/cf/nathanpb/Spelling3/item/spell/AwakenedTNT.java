package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class AwakenedTNT extends Spell{
    public AwakenedTNT() {
        setItemStack(new ItemStack(Material.BLAZE_ROD));
        setManaCost(250);
        setDescription("The power of the explosive in your hands");
        setDisplayName(ChatColor.GOLD + "Awakened TNT");

        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
        Triggers.add(ActionType.CLICK_ENTITY);

    }

    @Override
    public void execute(Object... args) {
        super.execute(e);
        Vector looking = entity.getEntity().getEyeLocation().getDirection().multiply(2);
        TNTPrimed tnt = entity.getEntity().getWorld().spawn(entity.getEntity().getEyeLocation().add(looking.getX(),
                looking.getY(), looking.getZ()), TNTPrimed.class);
        tnt.setVelocity(looking);

    }
}


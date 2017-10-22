package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator(priority = 0)
public class WindBlow extends Spell{
    public WindBlow() {
        setItemStack(new ItemStack(Material.BONE));
        setManaCost(10);
        setDescription("Let your enemies to fly... And fall like a shit");
        setDisplayName(ChatColor.GOLD + "WindBlow");

        Triggers.add(ActionType.CLICK_ENTITY);
    }

    @Override
    public boolean preExecute(Object... args) {
        if(e.getClickedEntity() instanceof Player) return false;
        if(!(e.getClickedEntity() instanceof LivingEntity)) return false;
        return super.preExecute(args);
    }

    @Override
        public void execute(Object... args) {
            super.execute();
            e.getClickedEntity().setVelocity(new Vector(0,50,0));
        }
}

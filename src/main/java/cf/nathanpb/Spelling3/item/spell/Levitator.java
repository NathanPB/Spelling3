package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class Levitator extends Spell{
    public Levitator() {
        setItemStack(new ItemStack(Material.FEATHER));
        setManaCost(10);
        setDescription("Control the wind, be the god of the air");
        setDisplayName(ChatColor.GOLD + "Levitator");


        Triggers.add(ActionType.CLICK_ENTITY);
        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
    }

    @Override
    public boolean preExecute(Object... args) {
        boolean exec = super.preExecute(e, args);
        if(exec){
            if(entity instanceof SpellingPlayer) {
                ((Player)  entity.getEntity()).setAllowFlight(true);
            }
        }
        return exec;
    }

    @Override
    public void execute(Object... args) {
        super.execute(e);
        Vector looking = entity.getEntity().getEyeLocation().getDirection();
        if(entity.getEntity().isGliding()){
            looking = looking.multiply(2);
        }
        entity.getEntity().setVelocity(looking);
    }

    @Override
    public void postExecute(Object... args) {
        super.postExecute(e);
        if(entity instanceof SpellingPlayer) {
            Player p = ((SpellingPlayer) entity).getPlayer();
            if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) return;
            p.setAllowFlight(false);
        }
    }
}

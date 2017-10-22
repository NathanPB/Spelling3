package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class MagicBreaker extends Spell{
    private static ArrayList<Snowball> snowball = new ArrayList<>();
    public MagicBreaker() {
        setItemStack(new ItemStack(Material.IRON_PICKAXE));
        setManaCost(3);
        setDescription("Become the cave's god");
        setDisplayName(ChatColor.GOLD + "Magic Breaker");

        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.HIT_BLOCK);

    }

    @Override
    public boolean preExecute(Object... args) {
        if(e.getAction().name().contains("RIGHT")) setManaCost(20);
        return super.preExecute(args);
    }

    @Override
    public void execute(Object... args) {
        super.execute(e);
        if(e.getAction().equals(ActionType.HIT_BLOCK)) e.getClickedBlock().breakNaturally();

        if(e.getAction().name().contains("AIR")){
            Vector looking = e.getTrigger().getEntity().getEyeLocation().getDirection();
            Snowball ball = e.getTrigger().getEntity().getWorld().spawn(e.getTrigger().getEntity().
                    getEyeLocation().add(looking.getX(),
                    looking.getY(), looking.getZ()), Snowball.class);
            ball.setVelocity(looking);
            ball.setGravity(false);
            snowball.add(ball);
        }
    }


    @EventHandler
    public static void onBallBreak(ProjectileHitEvent e){
        if(e.getHitBlock() == null) return;
        if(!snowball.contains(e.getEntity())) return;
        snowball.remove(e.getEntity());
        e.getHitBlock().breakNaturally();
    }
}

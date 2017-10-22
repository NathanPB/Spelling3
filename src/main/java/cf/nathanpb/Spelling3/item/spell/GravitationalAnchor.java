package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


/**
 * Created by nathanpb on 10/9/17.
 */
@ItemInitializator
public class GravitationalAnchor extends Spell{
    public GravitationalAnchor(){
        setItemStack(new ItemStack(Material.GLASS_BOTTLE));
        setDescription("Pull things around you, be the center of the gravity");
        setDisplayName(ChatColor.GOLD+"Gravitational Anchor");
        setManaCost(2);

        Triggers.add(ActionType.CLICK_ENTITY);
        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
    }

    @Override
    public boolean preExecute(Object... args) {
        if(getPlayer().isSneaking()) setManaCost(50);
        return super.preExecute(args);
    }

    @Override
    public void execute(Object... args) {
        for(Entity en : getPlayer().getLocation().getWorld().getNearbyEntities(getPlayer().getLocation(), 10.0D, 10.0D, 10.0D)){
            if(en instanceof Item) pullIt(en);
            if(en instanceof LivingEntity && getPlayer().isSneaking())   pullIt(en);
        }
        super.execute(args);
    }
    public void pullIt(Entity en){
        //https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/
        double dX = getPlayer().getLocation().getX() - en.getLocation().getX();
        double dY = getPlayer().getLocation().getY() - en.getLocation().getY();
        double dZ = getPlayer().getLocation().getZ() - en.getLocation().getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double X = Math.sin(pitch) * Math.cos(yaw);
        double Y = Math.sin(pitch) * Math.sin(yaw);
        double Z = Math.cos(pitch);
        Vector vector = new Vector(X, Z, Y);
        vector = en.getLocation().toVector().subtract(getPlayer().getLocation().toVector());
        Vector from = new Vector(getPlayer().getLocation().getX(), getPlayer().getEyeLocation().getY(), getPlayer().getLocation().getZ());
        Vector to  = new Vector(en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ());
        vector = from.subtract(to);
        vector.multiply(0.1);
        en.setVelocity(vector);
        en.getWorld().spawnParticle(Particle.TOWN_AURA, en.getLocation(),100);
    }
}

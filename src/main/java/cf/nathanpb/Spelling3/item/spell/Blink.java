package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.events.InteractSpellEvent;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

/**
 * Created by nathanpb on 9/15/17.
 */
@ItemInitializator
public class Blink extends Spell{
    public Blink() {
        setItemStack(new ItemStack(Material.EMERALD));
        setManaCost(0);
        setDescription("Why are you on all places at the same time?");
        setDisplayName(ChatColor.GOLD + "Blink");

        Triggers.add(ActionType.CLICK_ENTITY);
        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
    }


    @Override
    public boolean preExecute(Object... args) {
        if(!(e.getTrigger() instanceof SpellingPlayer)) return false;

        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(this.getItemStack());
        if(getPlayer().isSneaking()) return true;
        if(stack.getTag() != null && stack.getTag().hasKey("location")) {
            if(!getPlayer().getWorld().equals(getTeleportLocation(e).getWorld())){
                setManaCost(1000);
            } else {
                setManaCost((int) Math.round(getPlayer().getLocation().distance(getTeleportLocation(e))));
            }
        }
        return super.preExecute(args);
    }

    @Override
    public void execute(Object... args) {
        super.execute();
        if(getPlayer().isSneaking()) {
            setLocation(e);
            return;
        }
        Location loc = getTeleportLocation(e);
        getPlayer().teleport(loc);
        getPlayer().playSound(loc, Sound.ENTITY_ENDERMEN_TELEPORT, 10, 10);
        loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 10);
    }

    private void setLocation(InteractSpellEvent e){
        try {
            JSONObject o = new JSONObject();
            Location loc = getPlayer().getLocation();
            o.put("world", loc.getWorld().getName());
            o.put("x", Math.round(loc.getX()));
            o.put("y", Math.round(loc.getY()));
            o.put("z", Math.round(loc.getZ()));
            net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(this.getItemStack());
            if(stack.getTag() == null) stack.setTag(new NBTTagCompound());
            stack.getTag().setString("location", o.toString());
            getPlayer().setItemInHand(CraftItemStack.asBukkitCopy(stack));
        }catch (Exception e2){
            e2.printStackTrace();
        }
    }
    private Location getTeleportLocation(InteractSpellEvent e){
        Location loc = getPlayer().getLocation();
        try {
            net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(this.getItemStack());
            JSONObject json = new JSONObject(stack.getTag().getString("location"));
            loc.setWorld(Bukkit.getWorld(json.getString("world")));
            loc.setX(Double.valueOf(json.getInt("x")));
            loc.setY(Double.valueOf(json.getInt("y")));
            loc.setZ(Double.valueOf(json.getInt("z")));
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return loc;
    }
}

package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.material.KnowledgeFragment;
import cf.nathanpb.Spelling3.recipes.RecipeSpellTable;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by nathanpb on 9/10/17.
 */

@ItemInitializator
public class AuraShockwave extends Spell{

    public AuraShockwave() {
        setItemStack(new ItemStack(Material.GLOWSTONE_DUST));
        setManaCost(500);
        setDescription("Can throw your enemies far away...");
        setDisplayName(ChatColor.GOLD + "Aura Shockwave");

        setRecipe(new RecipeSpellTable(this)
                .shape(
                    new ItemStack(Material.END_CRYSTAL), new ItemStack(Material.END_ROD), new ItemStack(Material.END_CRYSTAL),
                    new ItemStack(Material.SHIELD), SpellingItem.getInstance(KnowledgeFragment.class).getItemStack(), new ItemStack(Material.SHIELD),
                    new ItemStack(Material.GHAST_TEAR), new ItemStack(Material.DIAMOND), new ItemStack(Material.GHAST_TEAR)
                ));

        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
        Triggers.add(ActionType.CLICK_ENTITY);
    }


    @Override
    public void execute(Object... args) {
        super.execute();
        for (Entity en : entity.getEntity().getWorld().getNearbyEntities(entity.getEntity().getLocation(), 15.0D, 15.0D, 15.0D)) {
            if (en instanceof LivingEntity) {
                if (!(en.equals(entity.getEntity()))) {
                    //https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/
                    double dX = entity.getEntity().getLocation().getX() - en.getLocation().getX();
                    double dY = entity.getEntity().getLocation().getY() - en.getLocation().getY();
                    double dZ = entity.getEntity().getLocation().getZ() - en.getLocation().getZ();
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                    double X = Math.sin(pitch) * Math.cos(yaw);
                    double Y = Math.sin(pitch) * Math.sin(yaw);
                    double Z = Math.cos(pitch);
                    Vector vector = new Vector(X, Z, Y);
                    vector = en.getLocation().toVector().subtract(entity.getEntity().getLocation().toVector());
                    Vector from = new Vector(entity.getEntity().getLocation().getX(), entity.getEntity().getLocation().getY(), entity.getEntity().getLocation().getZ());
                    Vector to = new Vector(en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ());
                    vector = to.subtract(from);
                    vector = vector.multiply(2);
                    vector = vector.setY(2);
                    en.setVelocity(vector);
                }
            }
        }
    }
}

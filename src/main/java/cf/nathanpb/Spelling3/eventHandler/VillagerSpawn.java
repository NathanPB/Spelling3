package cf.nathanpb.Spelling3.eventHandler;

import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.material.KnowledgeFragment;
import cf.nathanpb.Spelling3.recipes.VillagerTrade;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by nathanpb on 10/13/17.
 */
public class VillagerSpawn implements Listener{
    @EventHandler
    public static void spawn(CreatureSpawnEvent e){
        if(e.getEntity().getType().equals(EntityType.VILLAGER)){
            Villager v = ((Villager) e.getEntity());
            for(VillagerTrade trade : Core.getRecipesFor(VillagerTrade.class)){
                if(trade.getProfessions().contains(v.getProfession())){
                    if(trade.shouldSpawn()){
                        EntityVillager villager = (EntityVillager) ((CraftEntity)v).getHandle();
                        try {
                            Field recipes = villager.getClass().getDeclaredField("trades");
                            recipes.setAccessible(true);
                            MerchantRecipeList list = (MerchantRecipeList) recipes.get(villager);
                            list.add(((VillagerTrade)SpellingItem.getInstance(KnowledgeFragment.class).getSpellingRecipe()).getRecipe());
                            list.sort( (o1, o2) -> new Random().nextInt(3)-1);
                            recipes.set(villager, list);
                        }catch (Exception e2){
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

package cf.nathanpb.Spelling3.item.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by nathanpb on 10/8/17.
 */
@ItemInitializator
public class Hephaestum extends Spell{
    public Hephaestum() {
        setItemStack(new ItemStack(Material.EXP_BOTTLE));
        setManaCost(0);
        setDescription("Invoke Hephaestum to repair your soul");
        setDisplayName(ChatColor.GOLD + "Hephaestum");

        Triggers.add(ActionType.CLICK_ENTITY);
        Triggers.add(ActionType.CLICK_AIR);
        Triggers.add(ActionType.CLICK_BLOCK);
    }

    private ArrayList<ItemStack> itensToRepair = new ArrayList<>();


    public int getCost(ItemStack[] contents){
        int TotalDurability = 0;
        // DETECTAR MENDING
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) {
                if (contents[i].hasItemMeta()) {
                    if (contents[i].getItemMeta().hasEnchants()) {
                        Map<Enchantment, Integer> enchantment = contents[i]
                                .getEnchantments();
                        if (enchantment.containsKey(Enchantment.MENDING)) {
                            itensToRepair.add(contents[i]);
                        }
                    }
                }
            }
        }
        for (ItemStack stack : itensToRepair) {
            TotalDurability += stack.getDurability();
        }
        return TotalDurability;
    }

    @Override
    public boolean preExecute(Object... args) {
        setManaCost(getCost(((SpellingPlayer)e.getTrigger()).getPlayer().getInventory().getContents()));
        return super.preExecute(e, args);
    }

    @Override
    public void execute(Object... args) {
        Player p = ((SpellingPlayer)e.getTrigger()).getPlayer();
        for (ItemStack stack : this.itensToRepair) {
            stack.setDurability((short)0);
        }
        getPlayer().setTotalExperience(new SpellingPlayer(getPlayer()).getTotalExperience() - (getManaCost() * 2));
        p.sendMessage(ChatColor.GOLD+ ((Integer)getManaCost()).toString()+ChatColor.BLUE+ " durability points were recovered successfully");;
    }

    @Override
    public void postExecute(Object... args) {
        super.postExecute(args);
        itensToRepair.clear();
    }
}

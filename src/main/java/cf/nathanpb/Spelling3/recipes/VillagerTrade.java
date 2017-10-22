package cf.nathanpb.Spelling3.recipes;

import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.eventHandler.InventoryClick;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.MerchantRecipeIcon;
import cf.nathanpb.Spelling3.item.inventory.ReturnButton;
import net.minecraft.server.v1_12_R1.MerchantRecipe;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by nathanpb on 10/13/17.
 */
public class VillagerTrade extends MerchantRecipe implements SpellingRecipe{

    private List<Villager.Profession> professions = new ArrayList<>();
    private Integer chance = 0;

    public VillagerTrade(ItemStack b1, ItemStack b2, ItemStack s){
        super(CraftItemStack.asNMSCopy(b1), CraftItemStack.asNMSCopy(b2), CraftItemStack.asNMSCopy(s));
    }

    @Override
    public BookEntry getHolder() {
        BookEntry holder = new BookEntry(ChatColor.BLUE+"Recipe: "+ CraftItemStack.asBukkitCopy(sellingItem).getItemMeta().getDisplayName(), 3);
        holder.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_LEFT);
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_RIGHT);
        holder.addButton(SpellingItem.getInstance(MerchantRecipeIcon.class), BookEntry.SIDE.MIDDLE);
        holder.addButton(SpellingItem.getInstance(BlankSpace.class), 12);
        holder.addButton(SpellingItem.getInstance(BlankSpace.class), 14);

        if(getBuyItem1() != null)
            holder.addButton(CraftItemStack.asBukkitCopy(getBuyItem1()), 10);

        if(getBuyItem2() != null)
            holder.addButton(CraftItemStack.asBukkitCopy(getBuyItem2()), 11);

        holder.addButton(CraftItemStack.asBukkitCopy(sellingItem), 15);
        InventoryClick.block(holder);
        return holder;
    }

    public MerchantRecipe getRecipe() {
        return this;
    }

    @Override
    public SpellingItem getSResult() {
        return SpellingItem.getInstance(CraftItemStack.asBukkitCopy(this.sellingItem));
    }

    public VillagerTrade addProfession(Villager.Profession... p){
        Arrays.stream(p).forEach(profession -> professions.add(profession));
        return this;
    }
    public VillagerTrade setChance(Integer i){
        this.chance = i;
        return this;
    }

    public boolean shouldSpawn(){
        return this.chance-1 <=  new Random().nextInt(100);
    }

    public List<Villager.Profession> getProfessions() {
        return professions;
    }
}

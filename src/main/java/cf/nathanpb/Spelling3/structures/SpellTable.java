package cf.nathanpb.Spelling3.structures;

import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.SpellTableCraftButton;
import cf.nathanpb.Spelling3.recipes.RecipeSpellTable;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by nathanpb on 10/13/17.
 */
public class SpellTable implements Listener{
    public static ArrayList<SpellTable> tableList = new ArrayList<>();
    private BookEntry entry;
    private Location location;

    public SpellTable(Location l){
        this.location = l;
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());

        entry = new BookEntry(ChatColor.BLUE+"Spell Table", 5);
        entry.mkBorder(SpellingItem.getInstance(BlankSpace.class));

        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 10);
        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 19);
        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 28);

        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 10+6);
        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 19+6);
        entry.addButton(SpellingItem.getInstance(SpellTableCraftButton.class), 28+6);


        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 11);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 20);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 29);

        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 11+4);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 20+4);
        entry.addButton(SpellingItem.getInstance(BlankSpace.class), 29+4);
        tableList.add(this);
    }
    public static boolean check(Block b){
        Location l = b.getLocation();
        if(b.getType().equals(Material.CARPET)){
            l.setY(l.getY()-1);
            return l.getBlock().getType().equals(Material.BONE_BLOCK);
        }

        if(b.getType().equals(Material.BONE_BLOCK)){
            l.setY(l.getY()+1);
            return l.getBlock().getType().equals(Material.CARPET);
        }
        return false;
    }

    public BookEntry getGui(){
        return entry;
    }

    @EventHandler
    public void onICE(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().equals(getGui())){
            if(e.getCurrentItem().equals(SpellingItem.getInstance(SpellTableCraftButton.class).getItemStack())){
                ItemStack[] stacks = new ItemStack[getGridSlots().length];
                for(int i=0; i < getGridSlots().length; i++){
                    stacks[i] = e.getClickedInventory().getItem(getGridSlots()[i]);
                }
                for(RecipeSpellTable r : Core.getRecipesFor(RecipeSpellTable.class)){
                    if(r.match(stacks)){
                        e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getLocation(), r.getResult());
                        e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 10, 10);
                        for(ItemStack s : stacks){
                            s.setAmount(s.getAmount()-1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onICE(InventoryCloseEvent e){
        if(e.getInventory().equals(getGui())){
            dropAll();
            tableList.remove(this);
        }
    }

    public void dropAll(){
        for(ItemStack s : getGui().getContents()){
            if(SpellingItem.getInstance(BlankSpace.class).getItemStack().equals(s) || SpellingItem.getInstance(SpellTableCraftButton.class).getItemStack().equals(s) || s == null) continue;
            location.getWorld().dropItem(location, s);
        }
    }

    public Integer[] getGridSlots(){
        return new Integer[]{12, 13, 14, 21, 22, 23, 30, 31, 32};
    }

}

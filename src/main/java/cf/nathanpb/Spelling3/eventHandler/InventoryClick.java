package cf.nathanpb.Spelling3.eventHandler;

import cf.nathanpb.Spelling3.commands.Spelling;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Commands;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCrafting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Created by nathanpb on 10/10/17.
 */
public class InventoryClick implements Listener{

    private static ArrayList<Inventory> inventories = new ArrayList<>();
    private static HashMap<ItemStack, Consumer<InventoryClickEvent>> executors = new HashMap<>();

    @EventHandler
    public static void listener(InventoryClickEvent e) {
        if (inventories.contains(e.getClickedInventory())) e.setCancelled(true);

        if (e.getClickedInventory() != null) {
            if(e.getClickedInventory() instanceof CraftingInventory) checkShapedRecipes((CraftInventory) e.getInventory());
            if (e.getClickedInventory().equals(SpellingItem.getInstance(Commands.class).getHolder().getInventory())) {
                if(executors.containsKey(e.getCurrentItem())) executors.get(e.getCurrentItem()).accept(e);
                if (e.getCurrentItem() == null) return;
                if (!e.getCurrentItem().hasItemMeta()) return;
                if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
                if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith(ChatColor.GOLD + "")) {
                    Spelling.getAllCommands().forEach(cmd -> {
                        String name = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll(ChatColor.GOLD + "", "");
                        if (cmd.Trigger().equalsIgnoreCase(name)) {
                            Spelling.plzSendHelp(e.getWhoClicked(), cmd);
                        }
                    });
                }
            }
        }
    }
    public static void block(Inventory inv){
        inventories.add(inv);
    }
    private static void checkShapedRecipes(CraftInventory inv){
        CraftInventoryCrafting cr = (CraftInventoryCrafting) inv;
        for(int i=0; i<=cr.getSize();i++){
            cr.setItem(i, new ItemStack(Material.DIAMOND));
        }
        cr.setResult(new ItemStack(Material.CHORUS_FRUIT_POPPED));

    }

    public static void setExecutor(ItemStack i, Consumer<InventoryClickEvent> e){
        executors.put(i,e);
    }
}

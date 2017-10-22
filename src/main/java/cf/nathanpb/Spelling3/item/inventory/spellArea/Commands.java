package cf.nathanpb.Spelling3.item.inventory.spellArea;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.commands.Spelling;
import cf.nathanpb.Spelling3.eventHandler.InventoryClick;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.ReturnButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nathanpb on 10/13/17.
 */
@ItemInitializator(priority = -2)
public class Commands extends BookArea {
    public Commands(){
        setItemStack(new ItemStack(Material.PAPER));
        setDisplayName(ChatColor.GOLD+"Commands");
        addLore("All commands about Spelling");
    }

    @Override
    public void setup() {
        List<Spelling.Command> cmds = Spelling.getAllCommands();
        int var = 0;
        for(int i = cmds.size()-1; i >= 0; i--){
            if(i%7 == 0){
                var += 9;
            }
        }
        var+=18;
        holder = new BookEntry(ChatColor.BLUE+"Spells", var/9);
        holder.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_LEFT);
        holder.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_RIGHT);
        cmds.forEach(cmd ->{
            ItemStack stack = new ItemStack(Material.PAPER);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD+cmd.Trigger());
            meta.setLore(new ArrayList<>(Arrays.asList(new String[]{"/Spelling "+cmd.Trigger()})));
            stack.setItemMeta(meta);
            InventoryClick.setExecutor(stack, e ->{
                Spelling.plzSendHelp(e.getWhoClicked(), cmd);
                e.setCancelled(true);
            });
            holder.addButton(stack);
        });
        InventoryClick.block(holder);
    }

}

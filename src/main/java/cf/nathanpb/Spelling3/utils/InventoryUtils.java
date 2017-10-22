package cf.nathanpb.Spelling3.utils;

import cf.nathanpb.Spelling3.book.BookEntry;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.*;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanpb on 9/15/17.
 */
public class InventoryUtils {
    public static BookEntry mkHolder(SpellingItem item){
        BookEntry inv = new BookEntry(ChatColor.BLUE+"Info: "+item.getItemStack().getItemMeta().getDisplayName(), 3);
        inv.mkBorder(SpellingItem.getInstance(BlankSpace.class));
        inv.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_LEFT);
        inv.addButton(SpellingItem.getInstance(ReturnButton.class), BookEntry.SIDE.DOWN_RIGHT);
        inv.addButton(SpellingItem.getInstance(OpButton.class), BookEntry.SIDE.MIDDLE_DOWN);

        inv.addButton(SpellingItem.getInstance(CraftingButton.class), 10);
        inv.addButton(SpellingItem.getInstance(CraftingButton.class), 11);

        inv.addButton(SpellingItem.getInstance(BlankSpace.class), 12);
        inv.addButton(SpellingItem.getInstance(BlankSpace.class), 14);

        inv.addButton(SpellingItem.getInstance(DescriptionButton.class), 15);
        inv.addButton(SpellingItem.getInstance(DescriptionButton.class), 16);
        inv.addButton(item, BookEntry.SIDE.MIDDLE);
        return inv;
    }
    public static ItemStack makeBook(String title, String author, String bookContent) {

        List<String> strings = new ArrayList<>();
        int index = 0;
        while (index < bookContent.length()) {
            strings.add(bookContent.substring(index, Math.min(index + 230,bookContent.length())));
            index += 230;
        }
        String[] pages = strings.toArray(new String[strings.size()]);


        ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
        net.minecraft.server.v1_12_R1.ItemStack nmsis = CraftItemStack.asNMSCopy(is);
        NBTTagCompound bd = new NBTTagCompound();
        bd.setString("title", title);
        bd.setString("author", author);
        NBTTagList bp = new NBTTagList();
        for(String text : pages) {
            bp.add(new NBTTagString(text));
        }
        bd.set("pages", bp);
        nmsis.setTag(bd);
        is = CraftItemStack.asBukkitCopy(nmsis);
        return is;
    }
}

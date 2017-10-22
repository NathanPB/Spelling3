package cf.nathanpb.Spelling3.book;

import cf.nathanpb.Spelling3.LanguageManager;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.BlankSpace;
import cf.nathanpb.Spelling3.item.inventory.OpButton;
import cf.nathanpb.Spelling3.utils.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by nathanpb on 10/13/17.
 */
public class BookItem extends SpellingItem{

    public BookArea getArea(){
        return null;
    }
    public BookEntry getHolder(Player p){
        return InventoryUtils.mkHolder(this);
    }

    public String getBookEntryText(String lang) {
        String text = LanguageManager.getText(getRegistry(), lang);
        if(text == null || text.isEmpty()) text = ChatColor.RED+"Oops, I don't have a description yet :/ Please tell my owner NathanPB#1957";
        return text;
    }
    public void setDescription(String s){
        addLore(s);
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        if(e.getClickedInventory().equals(getArea().getHolder())){
            e.setCancelled(true);
            BookEntry holder = getHolder((Player) e.getWhoClicked());
            if(!holder.isBookMode()) {
                holder.addButton(e.getWhoClicked().isOp() ? SpellingItem.getInstance(OpButton.class) : SpellingItem.getInstance(BlankSpace.class), BookEntry.SIDE.MIDDLE_DOWN);
                try {
                    BookEntry.TempMeta.put(e.getWhoClicked().getUniqueId().toString(), this);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            holder.open((Player) e.getWhoClicked());
        }
    }
}

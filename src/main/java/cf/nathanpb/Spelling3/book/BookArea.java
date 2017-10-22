package cf.nathanpb.Spelling3.book;

import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

/**
 * Created by nathanpb on 10/13/17.
 */
public class BookArea extends SpellingItem{
    protected BookEntry holder;
    private ArrayList<SpellingItem> items = new ArrayList<>();

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        super.onInventoryClick(e);
        e.setCancelled(true);
        getHolder().open((Player) e.getWhoClicked());
    }

    public BookEntry getHolder() {
        return holder;
    }

    public void setup(){}

    public void addItem(SpellingItem item){
        items.add(item);
    }
    public ArrayList<SpellingItem> getAreaItems(){
        return items;
    }
}

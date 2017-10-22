package cf.nathanpb.Spelling3.book;

import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.item.SpellingItem;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

/**
 * Created by nathanpb on 9/14/17.
 */
public class BookEntry extends CraftInventoryCustom{
    public static JSONObject TempMeta = new JSONObject(); //aka gambiarra
    private ItemStack book = null;

    public BookEntry(String name, int size){
        super(null, size*9, name);
    }
    public BookEntry(ItemStack book){
        super(null, 0);
        this.book = book;
    }

    public void mkBorder(SpellingItem item){
        for(int i=0; i<=8; i++) setItem(i, item.getItemStack());
        for(int i = getSize()-1; i >= getSize()-9; i--) setItem(i, item.getItemStack());
        int i=8;
        while(i<=getSize()-1){
            setItem(i, item.getItemStack());
            i+=9;
        }
        i=9;
        while(i<=getSize()-1){
            setItem(i, item.getItemStack());
            i+=9;
        }
    }
    public void addButton(SpellingItem stack, SIDE side){
        if(side == SIDE.UPPER_LEFT) setItem(0, stack.getItemStack());
        if(side == SIDE.UPPER_RIGHT) setItem(8, stack.getItemStack());
        if(side == SIDE.DOWN_LEFT) setItem(getSize()-9, stack.getItemStack());
        if(side == SIDE.DOWN_RIGHT) setItem(getSize()-1, stack.getItemStack());
        if(side == SIDE.MIDDLE_UP) setItem(4, stack.getItemStack());
        if(side == SIDE.MIDDLE) setItem(getSize()/2, stack.getItemStack());
        if(side == SIDE.MIDDLE_DOWN) setItem(getSize()-5, stack.getItemStack());
    }
    public void addButton(SpellingItem stack, int slot){
        inventory.setItem(slot, CraftItemStack.asNMSCopy(stack.getItemStack()));
    }
    public void addButton(SpellingItem stack){
        addItem(stack.getItemStack());
    }

    public void addButton(ItemStack stack){
        addItem(stack);
    }
    public void addButton(ItemStack stack, int slot){
        setItem(slot, stack);
    }

    public boolean isBookMode() {
        return book != null;
    }

    public void removeButton(int slot){
        setItem(slot, null);
    }
    public void open(Player p) {
        if(isBookMode()) {
            new SpellingPlayer(p).forceUse(book);
        } else {
            p.openInventory(this);
        }
    }

    public enum SIDE{
        UPPER_RIGHT,
        UPPER_LEFT,
        DOWN_RIGHT,
        DOWN_LEFT,
        MIDDLE_UP,
        MIDDLE,
        MIDDLE_DOWN;
    }


}

package cf.nathanpb.Spelling3.spell;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.entity.SpellingEntity;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.events.InteractSpellEvent;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.item.inventory.spellArea.Spells;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanpb on 9/10/17.
 */
public abstract class Spell extends BookItem {


    private int manaCost = 0;
    private int initialManaCost = 0;

    public InteractSpellEvent e;

    public SpellingEntity entity;

    public Player getPlayer(){
        return ((SpellingPlayer)entity).getPlayer();
    }

    public List<ActionType> Triggers = new ArrayList<>();

    public void trigger(InteractSpellEvent e, Object... args){
        this.entity = e.getTrigger();
        this.e = e;
        if(preExecute(args)){
            execute(args);
            postExecute(args);
        }
    }
    public boolean preExecute(Object... args){
        if(entity instanceof SpellingPlayer){
            if(((SpellingPlayer) entity).getBurnout() > ((SpellingPlayer) entity).getMaxBurnout()){
                ((SpellingPlayer) entity).getPlayer().sendMessage(ChatColor.RED+"Your burnout is too high!");
                return false;
            }
            if(((SpellingPlayer) entity).getMana() < manaCost){
                ((SpellingPlayer) entity).getPlayer().sendMessage(ChatColor.RED+"You have no mana!");
                return false;
            }
        }
        return true;
    }
    public void execute(Object... args){}
    public void postExecute(Object... args){
        if(entity instanceof SpellingPlayer){
            ((SpellingPlayer) entity).addExp(manaCost);
            ((SpellingPlayer) entity).addMana(-manaCost);
        }
        setManaCost(initialManaCost);
    }

    public Spell setManaCost(int manaCost) {
        if(initialManaCost <= 0){
            initialManaCost = manaCost;
        }
        this.manaCost = manaCost;
        return this;
    }
    public int getManaCost() {
        return manaCost;
    }

    public SpellingEntity getEntity() {
        return entity;
    }

    @Override
    protected void onSpellEvent(InteractSpellEvent e) {
        super.onSpellEvent(e);
        if(Triggers.contains(e.getAction())) trigger(e);
    }
    @Override
    protected void onRightClick(PlayerInteractEvent e) {
        super.onRightClick(e);
        e.setCancelled(true);
    }
    @Override
    protected void onLeftClick(PlayerInteractEvent e) {
        super.onLeftClick(e);
        e.setCancelled(true);
    }
    @Override
    protected void onInteractEntity(PlayerInteractEntityEvent e) {
        super.onInteractEntity(e);
        e.setCancelled(true);
    }
    @Override
    protected void onHurtEntity(EntityDamageByEntityEvent e) {
        super.onHurtEntity(e);
        e.setCancelled(true);
    }

    @Override
    public BookArea getArea() {
        return SpellingItem.getInstance(Spells.class);
    }
}

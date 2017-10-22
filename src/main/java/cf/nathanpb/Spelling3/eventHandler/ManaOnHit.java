package cf.nathanpb.Spelling3.eventHandler;

import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by nathanpb on 9/10/17.
 */
public class ManaOnHit implements Listener{
    @EventHandler
    public static void onHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            if(e.getEntity() instanceof Monster){
                SpellingPlayer player = new SpellingPlayer((Player)e.getDamager());
                if(player.getMana()+Math.round(e.getDamage()) > player.getMaxMana()){
                    player.setMana(player.getMaxMana());
                }else{
                    player.addMana((int) Math.round(e.getDamage()));
                }
            }
        }
    }
}

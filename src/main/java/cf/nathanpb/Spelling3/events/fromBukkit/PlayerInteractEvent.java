package cf.nathanpb.Spelling3.events.fromBukkit;

import cf.nathanpb.Spelling3.events.SpellingEvent;
import org.bukkit.event.Event;

/**
 * Created by nathanpb on 10/22/17.
 */
public class PlayerInteractEvent extends org.bukkit.event.player.PlayerInteractEvent implements SpellingEvent{
    private PlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent e){
        super(e.getPlayer(), e.getAction(), e.getItem(), e.getClickedBlock(), e.getBlockFace(), e.getHand());
    }

    public static PlayerInteractEvent build(Event from){
        return new PlayerInteractEvent((org.bukkit.event.player.PlayerInteractEvent) from);
    }
}
